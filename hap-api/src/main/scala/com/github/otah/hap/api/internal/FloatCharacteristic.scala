package com.github.otah.hap.api.internal

import scalajson.ast._

import scala.util.Try

trait FloatCharacteristic extends NumberCharacteristic[Float] {

  override final val format = "float"

  override def minStep = 1.0f

  override protected final lazy val formatMeta = FormatMeta(Float.MinValue, Float.MaxValue)(JNumber.apply)

  override protected def toJsonValue(v: Float): JValue = JNumber(v)

  override protected def fromJsonValue(jv: JValue): Float = jv match {
    case JBoolean(flag) => if (flag) 1 else 0
    case JNumber(numString) => Try(numString.toFloat) getOrElse 0
    case _ => 0
  }
}
