package com.github.otah.hap.api.characteristics

import spray.json._

import scala.util.Try

trait IntegerBasedCharacteristic extends NumberCharacteristic[Int] {

  override def minStep: Int = 1

  //noinspection ScalaUnnecessaryParentheses
  protected def intBounds: (Int => JsValue) => FormatMeta
  override protected final lazy val formatMeta = intBounds.apply(JsNumber.apply)

  override protected def fromJsonValue(jv: JsValue): Int = jv match {
    case JsBoolean(flag) => if (flag) 1 else 0
    case JsNumber(bigDecimal) => Try(bigDecimal.toInt) getOrElse 0
    case _ => 0
  }
}
