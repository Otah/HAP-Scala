package com.github.otah.hap.api.services.sensor

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._
import com.github.otah.hap.api.services.OptionalName

trait HumiditySensorService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.sensor.humidity

  def currentHumidity: CurrentRelativeHumidityCharacteristic

  def statusActive: Option[StatusActiveCharacteristic] = None

  override def characteristics: Seq[LowLevelCharacteristic] = Seq(currentHumidity) ++ name ++ statusActive
}
