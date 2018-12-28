package com.github.otah.hap.api

import internal._

trait RotationSpeedCharacteristic extends FloatPercentageBasedCharacteristic with ReadWriteNotify[Float] {

  override final val characteristicType = hap.characteristic.rotationSpeed

  override def description = "Speed of rotation"
}
