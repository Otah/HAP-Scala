package com.github.otah.hap.api.characteristics

trait CurrentTemperatureCharacteristic extends FloatCharacteristic with ReadNotify[Float] with Units.DegreesCelsius {

  override final val characteristicType = hap.characteristic.temperature.current

  override def description = "Current temperature"

  override def min = 0.0f
  override def max = 100.0f

  override def minStep = 0.1f
}
