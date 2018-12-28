package com.github.otah.hap.api

import internal._

trait BrightnessCharacteristic extends IntCharacteristic with IntegerPercentages with ReadWriteNotify[Int] {

  override final val characteristicType = hap.characteristic.brightness

  override def description = "Brightness"
}
