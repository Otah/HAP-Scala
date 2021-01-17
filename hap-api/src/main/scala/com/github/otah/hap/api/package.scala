package com.github.otah.hap

import spray.json._

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions

package object api {

  type Identified[+O] = (InstanceId, O)

  implicit class IdentifiedAccessoryExt[+A <: HomeKitAccessory](val tuple: Identified[A]) {
    def aid: InstanceId = tuple._1
    def accessory: A = tuple._2
  }

  implicit class IdentifiedServiceExt[+S <: AccessoryService](val tuple: Identified[S]) {
    def iid: InstanceId = tuple._1
    def service: S = tuple._2
  }

  implicit class IdentifiedCharacteristicExt[+C <: LowLevelCharacteristic](val tuple: Identified[C]) {
    def iid: InstanceId = tuple._1
    def characteristic: C = tuple._2
  }

  type Services = Seq[Identified[AccessoryService]]

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

  trait LowLevelCharacteristic {

    def asJson(instanceId: Int)(implicit ec: ExecutionContext): Future[JsObject]

    def readJsonValue()(implicit ec: ExecutionContext): Future[JsValue]

    def jsonWriter(implicit ec: ExecutionContext): Option[JsValue => Future[_]]

    def jsonValueNotifier(implicit ec: ExecutionContext): Option[LowLevelNotifier]
  }
}
