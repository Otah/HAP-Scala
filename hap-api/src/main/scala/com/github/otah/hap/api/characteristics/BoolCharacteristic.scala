package com.github.otah.hap.api.characteristics

import com.github.otah.hap.api.Characteristic

import sjsonnew.shaded.scalajson.ast._

trait BoolCharacteristic extends Characteristic[Boolean] {

  override final val format = "bool"

  override protected def toJsonValue(v: Boolean) = JBoolean(v)

  override protected def fromJsonValue(jv: JValue) = jv match {
    case JBoolean(booleanValue) => booleanValue
    case JNumber(intValueAsString) => intValueAsString == "1"
    case _ => false
  }
}
