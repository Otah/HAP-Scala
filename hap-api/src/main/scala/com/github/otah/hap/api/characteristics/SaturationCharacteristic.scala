package com.github.otah.hap.api.characteristics

trait SaturationCharacteristic extends FloatCharacteristic with ReadWriteNotify with Units.Percentage {

  override final val characteristicType = hap.characteristic.saturation

  override def min = 0f
  override def max = 100f
}
