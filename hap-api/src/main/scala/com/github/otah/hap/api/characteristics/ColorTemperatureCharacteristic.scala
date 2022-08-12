package com.github.otah.hap.api.characteristics

trait ColorTemperatureCharacteristic extends UInt32Characteristic with ReadWriteNotify with Units.None {

  override final val characteristicType = hap.characteristic.colorTemperature

  override def min = 50
  override def max = 400
}
