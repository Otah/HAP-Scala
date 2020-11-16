package com.github.otah.hap.api.characteristics

import com.github.otah.hap.api.Characteristic
import spray.json._

import scala.concurrent.ExecutionContext

trait StringCharacteristic extends Characteristic[String] {

  override final val format = "string"

  def maxLength: Int

  override protected def toJsonValue(v: String) = JsString(v)

  override protected def fromJsonValue(jv: JsValue) = jv match {
    case JsString(stringValue) => stringValue
    case JsBoolean(booleanValue) => booleanValue.toString
    case JsNumber(bigDecimal) => bigDecimal.toString
    case _ => ""
  }

  override def asJson(instanceId: Int)(implicit ec: ExecutionContext) = super.asJson(instanceId) map { orig =>
    orig.copy(orig.fields + ("maxLen" -> JsNumber(maxLength)))
  }
}
