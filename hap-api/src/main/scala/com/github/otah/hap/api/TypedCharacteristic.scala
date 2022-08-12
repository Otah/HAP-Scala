package com.github.otah.hap.api

import spray.json._

trait TypedCharacteristic[T] extends Characteristic2 {

  def toJsonValue(value: T): JsValue

  def fromJsonValue(json: JsValue): T

  def toJson(iid: InstanceId, value: T): JsObject = toJson(iid, toJsonValue(value)) //FIXME handle conversion errors?
}
