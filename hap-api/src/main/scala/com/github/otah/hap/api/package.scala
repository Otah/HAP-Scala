package com.github.otah.hap

import spray.json._

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions

package object api {

  type Identified[+O] = (InstanceId, O)
  type Required[+O] = Identified[O]
  type Optional[+O] = Option[Identified[O]]

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
