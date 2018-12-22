package com.github.otah.hap.api.characteristics

import com.github.otah.hap.api.Characteristic

import scala.concurrent.ExecutionContext
import scalajson.ast._

trait StringCharacteristic extends Characteristic[String] {

  override final val format = "string"

  def maxLength: Int

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
