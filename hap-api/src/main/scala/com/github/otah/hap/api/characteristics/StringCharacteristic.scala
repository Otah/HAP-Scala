package com.github.otah.hap.api.characteristics

import com.github.otah.hap.api.Characteristic
import sjsonnew.shaded.scalajson.ast._

import scala.concurrent.ExecutionContext

trait StringCharacteristic extends Characteristic[String] {

  override final val format = "string"

  def maxLength: Int // TODO make optional

  override protected def toJsonValue(v: String) = JString(v)

  override protected def fromJsonValue(jv: JValue) = jv match {
    case JString(stringValue) => stringValue
    case JBoolean(booleanValue) => booleanValue.toString
    case JNumber(intValueAsString) => intValueAsString
    case _ => ""
  }

  override def asJson(instanceId: Int)(implicit ec: ExecutionContext) = super.asJson(instanceId) map { orig =>
    orig.copy(orig.value + ("maxLen" -> JNumber(maxLength)))
  }
}
