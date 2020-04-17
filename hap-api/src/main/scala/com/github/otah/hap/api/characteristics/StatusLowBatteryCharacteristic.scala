package com.github.otah.hap.api.characteristics

trait StatusLowBatteryCharacteristic extends UInt8Characteristic with ReadNotify[Int] with Units.None {

  override final val characteristicType = hap.characteristic.statusLowBattery

  override def min = 0
  override def max = 1

  override def description = "Is the battery level low"
}

object StatusLowBatteryCharacteristic {

  val levelNormal = 0
  val levelLow = 1
}
