package com.github.otah.hap.api.services.sensor

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._
import com.github.otah.hap.api.services._

trait HumiditySensorService2 extends AccessoryService with OptionalName2 {

  override final val serviceType = hap.service.sensor.humidity

  def currentHumidity: Identified[CurrentRelativeHumidityCharacteristic]

  def statusActive: Option[Identified[StatusActiveCharacteristic]] = None

  override def characteristics = Characteristics(name, currentHumidity, statusActive)
}
