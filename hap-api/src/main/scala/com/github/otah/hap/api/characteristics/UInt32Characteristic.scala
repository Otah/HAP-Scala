package com.github.otah.hap.api.characteristics

import sjsonnew.shaded.scalajson.ast._

import scala.util.Try

trait UInt32Characteristic extends NumberCharacteristic[Long] {

  override final val format = "uint32"

  override def minStep: Long = 1

  override protected def formatMeta = FormatMeta(min = 0, max = 4294967295L)(JNumber.apply)

  override protected def fromJsonValue(jv: JValue): Long = jv match {
    case JBoolean(flag) => if (flag) 1 else 0
    case JNumber(numString) => Try(numString.toLong) getOrElse 0
    case _ => 0
  }
}
