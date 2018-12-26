package com.github.otah.hap.api.characteristics

trait ColorTemperatureCharacteristic extends UInt32Characteristic with ReadWriteNotify[Long] with Units.None {

  override final val characteristicType = hap.characteristic.colorTemperature

  override def description = "Color temperature"

  override def min = 50
  override def max = 400
}
