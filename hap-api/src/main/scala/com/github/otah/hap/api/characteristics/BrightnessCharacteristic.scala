package com.github.otah.hap.api.characteristics

trait BrightnessCharacteristic extends IntCharacteristic with IntegerPercentages with ReadWriteNotify[Int] {

  override final val characteristicType = hap.characteristic.brightness
}
