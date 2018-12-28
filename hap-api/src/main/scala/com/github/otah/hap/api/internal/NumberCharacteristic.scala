package com.github.otah.hap.api.internal

import scalajson.ast._

import scala.concurrent.ExecutionContext

trait NumberCharacteristic[T] extends Characteristic[T] {

  def min: T
  def max: T
  def unit: Option[String]

  def minStep: T

  protected case class FormatMeta(min: T, max: T)(val toJValue: T => JValue)(implicit val ordering: Ordering[T])
  protected def formatMeta: FormatMeta

  override protected def toJsonValue(v: T): JValue = {
    val ordering = formatMeta.ordering
    require(ordering.gteq(min, formatMeta.min), "Defined min value is lower than the min value of the used format")
    require(ordering.lteq(max, formatMeta.max), "Defined max value is lower than the max value of the used format")
    require(ordering.gteq(v, min), "Provided value is lower than the declared min value")
    require(ordering.lteq(v, max), "Provided value is greater than the declared max value")
    formatMeta.toJValue(v)
  }

  override def asJson(instanceId: Int)(implicit ec: ExecutionContext) = super.asJson(instanceId) map { orig =>
    val required = Map(
      "minValue" -> toJsonValue(min),
      "maxValue" -> toJsonValue(max),
      "minStep" -> toJsonValue(minStep),
    )
    val maybeUnit = unit map (u => "unit" -> JString(u))
    orig.copy(orig.value ++ required ++ maybeUnit)
  }

}
