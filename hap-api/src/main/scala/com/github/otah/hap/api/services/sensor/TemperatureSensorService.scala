package com.github.otah.hap.api.services.sensor

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._
import com.github.otah.hap.api.services._

trait TemperatureSensorService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.sensor.temperature

  def currentTemperature: Required[CurrentTemperatureCharacteristic]

  def statusActive: Optional[StatusActiveCharacteristic] = None

  override def characteristics = Characteristics(name, currentTemperature, statusActive)
}
