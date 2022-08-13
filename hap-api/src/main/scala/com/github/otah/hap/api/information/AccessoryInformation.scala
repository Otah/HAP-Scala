package com.github.otah.hap.api
package information

import characteristics.*
import com.github.otah.hap.api.information
import services.*
import spray.json.JsValue

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

trait AccessoryInformation extends SpecializedService {

  override final val serviceType = hap.service.accessoryInformation

  override def iid: InstanceId = InstanceId(1)

  val identify: Required[IdentifyCharacteristic]
  val manufacturer: Required[InfoCharacteristic.Manufacturer]
  val model: Required[InfoCharacteristic.Model]
  val name: Required[NameCharacteristic]
  val serialNumber: Required[InfoCharacteristic.SerialNumber]
  val firmwareRevision: Required[RevisionCharacteristic.Firmware]

  val hardwareRevision: Optional[RevisionCharacteristic.Hardware]
  val accessoryFlags: Optional[AccessoryFlagsCharacteristic]

  override def all: AllSupported = AllSupported(
    name,
    identify,
    manufacturer,
    model,
    serialNumber,
    firmwareRevision,
    hardwareRevision,
    accessoryFlags,
  )
}

object AccessoryInformation {

  trait FromInfo extends AccessoryInformation with FilterSubsetFromAll with InfoProvider {

    override def characteristicsWrite(updates: Map[InstanceId, JsValue])(implicit ec: ExecutionContext): Seq[Future[_]] = {
      // only identify should be writable
      updates.get(identify.iid).iterator.toSeq map (_ => Future.fromTry(Try(homeKitInfo.identification())))
    }

    override def characteristicsValues()(implicit ec: ExecutionContext): Map[InstanceId, Future[JsValue]] = futureValues

    //TODO accessory flags are hypothetically eventable...
    override def characteristicsSubscribe(callback: Map[InstanceId, JsValue] => Unit): Subscription = () => ()

    override val identify =
      new A with IdentifyCharacteristic //FIXME IdentifyCharacteristic(homeKitInfo.identification)
    override val manufacturer =
      new A with InfoCharacteristic.Manufacturer //FIXME InfoCharacteristic.manufacturer(homeKitInfo.manufacturer)
    override val model =
      new A with InfoCharacteristic.Model //FIXME InfoCharacteristic.model(homeKitInfo.model)
    override val name =
      new A with NameCharacteristic //FIXME NameCharacteristic(homeKitInfo.label) //TODO is label the correct info?
    override val serialNumber =
      new A with InfoCharacteristic.SerialNumber //FIXME InfoCharacteristic.serialNumber(homeKitInfo.serialNumber)
    override val firmwareRevision =
      new A with RevisionCharacteristic.Firmware //FIXME RevisionCharacteristic.firmware(homeKitInfo.firmwareRevision)
    override val hardwareRevision = homeKitInfo.hardwareRevision map { _ =>
      new A with RevisionCharacteristic.Hardware
    }
    override val accessoryFlags = homeKitInfo.accessoryFlags map { _ =>
      new A with AccessoryFlagsCharacteristic
    }

    private val values: Map[InstanceId, JsValue] = {
      val i = homeKitInfo
      Map(
        manufacturer withValue i.manufacturer,
        model withValue i.model,
        name withValue i.label, //TODO is label the correct info?
        serialNumber withValue i.serialNumber,
        firmwareRevision withValue i.firmwareRevision,
      ) ++ (
        hardwareRevision zip i.hardwareRevision map (_ withValue _)
      ) ++ (
        accessoryFlags zip AccessoryFlagsCharacteristic.flagsToBits(i.accessoryFlags) map (_ withValue _)
      )
    }

    private val futureValues = values.view.mapValues(Future.successful).toMap
  }

  /** Creates an Identified accessory info service from an info
    * @param info Info object to be translated to the respective info characteristics.
    * @return Identified service for accessory information
    */
  def fromInfo(info: HomeKitInfo): AccessoryInformation = {
    new FromInfo {
      override def homeKitInfo: HomeKitInfo = info
    }
  }
}
