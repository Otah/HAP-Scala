package com.github.otah.hap

import scalajson.ast.{JObject, JValue}

import scala.concurrent.{ExecutionContext, Future}

package object api {

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

  case class CharacteristicInstance(iid: InstanceId, characteristic: Option[LowLevelCharacteristic]) {

    def asJson(implicit ec: ExecutionContext): Option[Future[JObject]] = characteristic map (_.asJson(iid.value))
  }

  case class InstanceId(value: Int) {
    require(value > 0, "IID has to be positive number")

    def -(ch: LowLevelCharacteristic): CharacteristicInstance = CharacteristicInstance(this, Some(ch))
    def -(maybeCh: Option[LowLevelCharacteristic]): CharacteristicInstance = CharacteristicInstance(this, maybeCh)
  }
}
