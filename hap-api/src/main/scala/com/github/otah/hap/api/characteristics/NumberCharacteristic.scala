package com.github.otah.hap.api.characteristics

import com.github.otah.hap.api.Characteristic
import sjsonnew.shaded.scalajson.ast._

import scala.concurrent.ExecutionContext

trait NumberCharacteristic[T] extends Characteristic[T] {
  import Ordering.Implicits._

  def min: T
  def max: T
  def unit: Option[String]

  def minStep: T

  protected case class FormatMeta(min: T, max: T)
                                 (val toJValue: T => JValue)
                                 (implicit val ordering: Ordering[T])

  protected def formatMeta: FormatMeta

  override protected def toJsonValue(v: T): JValue = {
    implicit val ordering = formatMeta.ordering
    require(v >= min, s"Provided value $v is lower than the declared min value $min")
    require(v <= max, s"Provided value $v is greater than the declared max value $max")
    formatMeta.toJValue(v)
  }

  override def asJson(instanceId: Int)(implicit ec: ExecutionContext) = super.asJson(instanceId) map { orig =>
    implicit val ordering = formatMeta.ordering
    val required = Map(
      "minValue" -> {
        require(min >= formatMeta.min, s"Defined min value $min is lower than the min value of the used format (${formatMeta.min})")
        formatMeta.toJValue(min)
      },
      "maxValue" -> {
        require(max <= formatMeta.max, s"Defined max value $max is higher than the max value of the used format (${formatMeta.max})")
        formatMeta.toJValue(max)
      },
      "minStep" -> formatMeta.toJValue(minStep),
    )
    val maybeUnit = unit map (u => "unit" -> JString(u))
    orig.copy(orig.value ++ required ++ maybeUnit)
  }

}
