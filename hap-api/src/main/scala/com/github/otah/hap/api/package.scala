package com.github.otah.hap

import spray.json._

import scala.concurrent.{ExecutionContext, Future}

package object api {

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
