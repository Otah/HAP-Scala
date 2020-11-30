package com.github.otah.hap

import com.github.otah.hap.api.services.Characteristics
import spray.json._

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions

package object api {

  type Identified[+O] = (InstanceId, O)
  type Required[+O] = Identified[O]
  type Optional[+O] = Option[Identified[O]]

  implicit class IdentifiedAccessoryExt[+A <: HomeKitAccessory](val tuple: Identified[A]) extends HomeKitAccessory {
    def aid: InstanceId = tuple._1
    def accessory: A = tuple._2

    override def infoService: (InstanceId, AccessoryService) = accessory.infoService
    override def services: Services = accessory.services
  }

  implicit class IdentifiedServiceExt[+S <: AccessoryService](val tuple: Identified[S]) extends AccessoryService {
    def iid: InstanceId = tuple._1
    def service: S = tuple._2

    override def serviceType: HapType = service.serviceType
    override def characteristics: Characteristics = service.characteristics
  }

  implicit class IdentifiedCharacteristicExt[+C <: LowLevelCharacteristic](val tuple: Identified[C]) extends LowLevelCharacteristic {
    def iid: InstanceId = tuple._1
    def characteristic: C = tuple._2

    override def asJson(instanceId: Int)(implicit ec: ExecutionContext): Future[JsObject] = characteristic.asJson(instanceId)
    override def readJsonValue()(implicit ec: ExecutionContext): Future[JsValue] = characteristic.readJsonValue()
    override def jsonWriter(implicit ec: ExecutionContext): Option[JsValue => Future[_]] = characteristic.jsonWriter
    override def jsonValueNotifier(implicit ec: ExecutionContext): Option[LowLevelNotifier] = characteristic.jsonValueNotifier
  }

  type Services = Seq[Identified[AccessoryService]]

  implicit class IntIidExt(num: Int) {
    def <=>[O](obj: O): Identified[O] = InstanceId(num) <=> obj
  }

  trait Subscription {
    def unsubscribe(): Unit
  }

  trait TypedNotifier[T] {
    def subscribe(callback: T => Future[Unit]): Subscription
  }

  trait LowLevelNotifier {
    def subscribe(callback: JsValue => Future[Unit]): Subscription
  }

  trait LowLevelCharacteristic {

    def asJson(instanceId: Int)(implicit ec: ExecutionContext): Future[JsObject]

    def readJsonValue()(implicit ec: ExecutionContext): Future[JsValue]

    def jsonWriter(implicit ec: ExecutionContext): Option[JsValue => Future[_]]

    def jsonValueNotifier(implicit ec: ExecutionContext): Option[LowLevelNotifier]
  }
}
