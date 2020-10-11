package com.github.otah.hap.api.services.sensor

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._
import com.github.otah.hap.api.services._

trait TemperatureSensorService extends AccessoryService with OptionalName with Has3Characteristics {

  override final val serviceType = hap.service.sensor.temperature

  def currentTemperature: CurrentTemperatureCharacteristic

  def statusActive: Option[StatusActiveCharacteristic] = None

  override def characteristics = Characteristics(
    id1 -> name,
    id2 -> currentTemperature,
    id3 -> statusActive,
  )
}
