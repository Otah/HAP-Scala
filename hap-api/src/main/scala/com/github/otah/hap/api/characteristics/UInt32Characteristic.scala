package com.github.otah.hap.api.characteristics

import spray.json._

import scala.util.Try

trait UInt32Characteristic extends NumberCharacteristic[Long] {

  override final val format = "uint32"

  override def minStep: Long = 1

  override protected def formatMeta = FormatMeta(min = 0, max = 4294967295L)(JsNumber.apply)

  override def fromJsonValue(jv: JsValue): Long = jv match {
    case JsBoolean(flag) => if (flag) 1 else 0
    case JsNumber(bigDecimal) => Try(bigDecimal.toLong) getOrElse 0
    case _ => 0
  }
}
