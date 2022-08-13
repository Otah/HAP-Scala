package com.github.otah.hap.api
package characteristics

import spray.json._

import scala.concurrent.ExecutionContext

trait StringCharacteristic extends TypedCharacteristic[String] {

  override final val format = "string"

  def maxLength: Option[Int] = None

  override def toJsonValue(v: String) = JsString(v)

  override def fromJsonValue(jv: JsValue) = jv match {
    case JsString(stringValue) => stringValue
    case JsBoolean(booleanValue) => booleanValue.toString
    case JsNumber(bigDecimal) => bigDecimal.toString
    case _ => ""
  }

  override def toJson(value: String): JsObject = {
    val orig = super.toJson(value)
    orig.copy(orig.fields ++ (maxLength map (max => "maxLen" -> JsNumber(max))))
  }
}
