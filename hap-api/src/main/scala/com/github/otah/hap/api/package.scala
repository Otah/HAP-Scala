package com.github.otah.hap

import sjsonnew.shaded.scalajson.ast._

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions

package object api {

  type Identified[+O] = (InstanceId, O)
  type Required[+O] = Identified[O]
  type Optional[+O] = Option[Identified[O]]

  type Services = Seq[Identified[AccessoryService]]

  trait Subscription {
    def unsubscribe(): Unit
  }

  trait TypedNotifier[T] {
    def subscribe(callback: T => Future[Unit]): Subscription
  }

  trait LowLevelNotifier {
    def subscribe(callback: JValue => Future[Unit]): Subscription
  }

  trait LowLevelCharacteristic {

    def asJson(instanceId: Int)(implicit ec: ExecutionContext): Future[JObject]

    def readJsonValue()(implicit ec: ExecutionContext): Future[JValue]

    def jsonWriter(implicit ec: ExecutionContext): Option[JValue => Future[_]]

    def jsonValueNotifier(implicit ec: ExecutionContext): Option[LowLevelNotifier]
  }
}
