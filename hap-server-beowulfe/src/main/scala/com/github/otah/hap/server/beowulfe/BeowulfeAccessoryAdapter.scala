package com.github.otah.hap.server.beowulfe

import java.util
import java.util.concurrent.{CompletableFuture, ConcurrentHashMap}
import javax.json._

import com.github.blemale.scaffeine.Scaffeine
import com.github.otah.hap.api._
import io.github.hapjava.accessories.HomekitAccessory
import io.github.hapjava.characteristics.{Characteristic, EventableCharacteristic, HomekitCharacteristicChangeCallback}
import io.github.hapjava.services.Service
import spray.json._

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.jdk.CollectionConverters._

class BeowulfeAccessoryAdapter(aid: InstanceId, accessory: HomeKitAccessory)(implicit ec: ExecutionContext) extends HomekitAccessory {

  import BeowulfeAccessoryAdapter._

  override def identify(): Unit = accessory.identification()
  override def getId: Int = aid.value
  override def getName: CompletableFuture[String] = CompletableFuture.completedFuture(accessory.label)
  override def getManufacturer: CompletableFuture[String] = CompletableFuture.completedFuture(accessory.manufacturer)
  override def getModel: CompletableFuture[String] = CompletableFuture.completedFuture(accessory.model)
  override def getSerialNumber: CompletableFuture[String] = CompletableFuture.completedFuture(accessory.serialNumber)
  override def getFirmwareRevision: CompletableFuture[String] = CompletableFuture.completedFuture(null) //TODO provide firmware revision
  override def getServices: util.Collection[Service] = accessory.services.map(_.service).map { service =>
    // ignoring all IIDs due to FW limitations
    new Service {

      override def getType: String = service.serviceType.minimalForm

      override def getCharacteristics: util.List[Characteristic] =
        service.characteristics.map(_.characteristic).map(convertCharacteristic).asJava
    }
  }.asJava
}

object BeowulfeAccessoryAdapter {

  /** Importing this object you get an implicit conversion from [[HomeKitAccessory]] to Beowulfe's [[HomekitAccessory]].
    */
  object Implicit {
    import scala.language.implicitConversions

    implicit def accessoryToBeowulfe(accessory: Identified[HomeKitAccessory])(implicit ec: ExecutionContext): HomekitAccessory =
      new BeowulfeAccessoryAdapter(accessory.aid, accessory.accessory)
  }

  import JsonConverters._

  private def convertCharacteristic(orig: LowLevelCharacteristic)(implicit ec: ExecutionContext): Characteristic = {

    class BridgedCharacteristic extends Characteristic with AccessoryConversions {

      private val cache = Scaffeine().expireAfterWrite(1.seconds).build[String, JsValue]()
      private val key = ""

      protected def setTempValue(value: JsValue): Unit = cache.put(key, value)

      /** As [[supplyValue]] is called after each event
        * (in other words the Beowulfe's framework is not designed in the way that the emitted event determines the value),
        * let's store the event result for a second (see the [[cache]] above) and provide that value to immediate
        * subsequent call of [[supplyValue]].
        *
        * Another unfortunate, for which there is probably no workaround, is that
        * [[supplyValue]] has to be blocking from by design of the underlying framework.
        * Although it might seem that you can use the `characteristicBuilder` asynchronously,
        * this won't work as the builder is built immediately after this procedure returns.
        * By trying to use the builder asynchronously it would prevent the framework to get the value soon enough.
        *
        * This is especially unfortunate because with this limitation, there can't be multiple accessories
        * getting debounced "supply-value orders" at once and then responding all together in parallel.
        * If accessories share a value (or some base for it) the only chance is to use some time-based cache.
        */
      override def supplyValue(characteristicBuilder: JsonObjectBuilder): Unit = {
        val result = cache.getIfPresent(key) getOrElse Await.result(orig.readJsonValue(), 29.seconds)
        //TODO add error handling - now it fails the whole procedure if readJsonValue's Future fails
        addJToJson(characteristicBuilder, "value", result)
      }

      override def toJson(iid: Int): CompletableFuture[JsonObject] = orig.asJson(iid) map convertObjectJToJson

      override def setValue(jsonValue: JsonValue): Unit = orig.jsonWriter match {
        case Some(writer) => writer.apply(convertJsonToJ(jsonValue)) // ignoring the Future, as the return type is `Unit`
        case None => throw new IllegalStateException("This characteristic doesn't have write permission")
      }
    }

    orig.jsonValueNotifier match {

      // Provide just the bridged characteristic if this doesn't have notify permission
      case None => new BridgedCharacteristic

      // In case of notify permission, Beowulfe's framework recognizes this fact from implementing
      // the EventableCharacteristic interface, so we have to mix it into the bridged characteristic.
      case Some(notifier) => new BridgedCharacteristic with EventableCharacteristic {

        private val subscriptions = new ConcurrentHashMap[Subscription, Unit]()

        override def subscribe(callback: HomekitCharacteristicChangeCallback): Unit = {
          val subscription = notifier.subscribe { v =>
            setTempValue(v) // enable this notifier to supply value instead of getting it again
            Future(callback.changed())
          }

          subscriptions.put(subscription, ())
        }

        override def unsubscribe(): Unit = {
          subscriptions.keySet().asScala.toSet foreach { subscription: Subscription =>
            subscriptions.remove(subscription)
            subscription.unsubscribe()
          }
        }
      }
    }
  }
}
