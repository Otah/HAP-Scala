package com.github.otah.hap.api.characteristics

trait HueCharacteristic extends FloatCharacteristic with ReadWriteNotify[Float] with Units.ArcDegrees {

  override final val characteristicType = hap.characteristic.hue

  override def min = 0f
  override def max = 360f
}
