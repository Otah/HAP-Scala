package com.github.otah.hap.api.characteristics

trait RotationSpeedCharacteristic extends FloatPercentageBasedCharacteristic with ReadWriteNotify[Float] {

  override final val characteristicId = hap.characteristic.rotationSpeed

  override def description = "Speed of rotation"
}
