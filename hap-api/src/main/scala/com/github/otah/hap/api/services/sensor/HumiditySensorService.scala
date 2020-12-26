package com.github.otah.hap.api.services.sensor

import com.github.otah.hap.api.characteristics._
import com.github.otah.hap.api.services._

trait HumiditySensorService extends SpecializedService with OptionalName {

  override final val serviceType = hap.service.sensor.humidity

  def currentHumidity: Required[CurrentRelativeHumidityCharacteristic]

  def statusActive: Optional[StatusActiveCharacteristic] = None

  override def all: AllSupported = AllSupported(name, currentHumidity, statusActive)
}
