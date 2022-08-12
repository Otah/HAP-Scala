package com.github.otah.hap

import com.github.otah.hap.api.services.Characteristics
import spray.json._

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions

package object api {

  type Identified[+O] = (InstanceId, O)

  implicit class IdentifiedAccessoryExt[+A <: HomeKitAccessory](val tuple: Identified[A]) extends HomeKitAccessory {
    def aid: InstanceId = tuple._1
    def accessory: A = tuple._2

    override def infoService: Service = accessory.infoService
    override def services: Services = accessory.services
  }

  implicit class IdentifiedCharacteristicExt[+C <: Characteristic](val tuple: Identified[C]) extends Characteristic {
    def iid: InstanceId = tuple._1
    def characteristic: C = tuple._2

    override def characteristicType: HapType = characteristic.characteristicType
    override def isReadable: Boolean = characteristic.isReadable
    override def isWritable: Boolean = characteristic.isWritable
    override def hasEvents: Boolean = characteristic.hasEvents
    override def format: String = characteristic.format
    override def description: Option[String] = characteristic.description
  }

  type Services = Seq[Service]

  implicit class IntIidExt(num: Int) extends InstanceId.Ops {
    def asInstanceId: InstanceId = InstanceId(num)
  }

  implicit def opsToIid(ops: InstanceId.Ops): InstanceId = ops.asInstanceId

  trait Subscription {
    def unsubscribe(): Unit
  }

  trait TypedNotifier[T] {
    def subscribe(callback: T => Future[Unit]): Subscription
  }

  trait LowLevelNotifier {
    def subscribe(callback: JsValue => Future[Unit]): Subscription
  }
}
