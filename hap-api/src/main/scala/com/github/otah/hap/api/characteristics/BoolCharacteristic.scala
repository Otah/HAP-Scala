package com.github.otah.hap.api.characteristics

import com.github.otah.hap.api.Characteristic

import spray.json._

trait BoolCharacteristic extends Characteristic[Boolean] {

  override final val format = "bool"

  override protected def toJsonValue(v: Boolean) = JsBoolean(v)

  override protected def fromJsonValue(jv: JsValue) = jv match {
    case JsBoolean(booleanValue) => booleanValue
    case JsNumber(bigDecimal) => bigDecimal == BigDecimal(1)
    case _ => false
  }
}
