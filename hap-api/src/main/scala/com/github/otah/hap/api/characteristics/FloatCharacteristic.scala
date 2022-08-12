package com.github.otah.hap.api.characteristics

import spray.json._

import scala.util.Try

trait FloatCharacteristic extends NumberCharacteristic[Float] {

  override final val format = "float"

  override def minStep = 1.0f

  override protected final lazy val formatMeta = FormatMeta(Float.MinValue, Float.MaxValue)(JsNumber.apply)

  override def fromJsonValue(jv: JsValue): Float = jv match {
    case JsBoolean(flag) => if (flag) 1 else 0
    case JsNumber(bigDecimal) => Try(bigDecimal.toFloat) getOrElse 0
    case _ => 0
  }
}
