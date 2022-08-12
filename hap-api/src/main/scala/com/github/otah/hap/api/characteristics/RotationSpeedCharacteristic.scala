package com.github.otah.hap.api.characteristics

trait RotationSpeedCharacteristic extends FloatPercentageBasedCharacteristic with ReadWriteNotify {

  override final val characteristicType = hap.characteristic.rotationSpeed
}
