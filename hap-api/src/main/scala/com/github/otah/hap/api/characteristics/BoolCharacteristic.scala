package com.github.otah.hap.api
package characteristics

import spray.json._

trait BoolCharacteristic extends TypedCharacteristic[Boolean] {

  override final val format = "bool"

  override def toJsonValue(v: Boolean) = JsBoolean(v)

  override def fromJsonValue(jv: JsValue) = jv match {
    case JsBoolean(booleanValue) => booleanValue
    case JsNumber(bigDecimal) => bigDecimal == BigDecimal(1)
    case _ => false
  }
}
