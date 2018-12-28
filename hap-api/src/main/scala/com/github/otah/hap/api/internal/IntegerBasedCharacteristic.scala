package com.github.otah.hap.api.internal

import scalajson.ast._

import scala.util.Try

trait IntegerBasedCharacteristic extends NumberCharacteristic[Int] {

  override def minStep: Int = 1

  //noinspection ScalaUnnecessaryParentheses
  protected def intBounds: (Int => JValue) => FormatMeta
  override protected final lazy val formatMeta = intBounds.apply(JNumber.apply)

  override protected def toJsonValue(v: Int): JValue = JNumber(v)

  override protected def fromJsonValue(jv: JValue): Int = jv match {
    case JBoolean(flag) => if (flag) 1 else 0
    case JNumber(numString) => Try(numString.toInt) getOrElse 0
    case _ => 0
  }
}
