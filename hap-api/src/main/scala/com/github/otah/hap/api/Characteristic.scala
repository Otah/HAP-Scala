package com.github.otah.hap.api

import spray.json._

trait Characteristic extends TypeConvenience {

  def characteristicType: HapType
  def description: Option[String] = None

  def format: String

  def isReadable: Boolean
  def isWritable: Boolean
  def hasEvents: Boolean

  def toJson(iid: InstanceId, value: JsValue): JsObject = {
    val perms = Option.when(isReadable)("pr") ++ Option.when(isWritable)("pw") ++ Option.when(hasEvents)("ev")
    JsObject {
      Map(
        "iid" -> JsNumber(iid.value),
        "type" -> JsString(characteristicType.minimalForm),
        "format" -> JsString(format),
        "value" -> value,
        "perms" -> JsArray(perms.toVector map JsString.apply),
      ) ++ {
        description map (d => "description" -> JsString(d))
      }
    }
  }
}
