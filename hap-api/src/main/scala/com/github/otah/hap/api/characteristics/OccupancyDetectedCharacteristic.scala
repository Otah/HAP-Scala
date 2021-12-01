package com.github.otah.hap.api.characteristics

trait OccupancyDetectedCharacteristic extends UInt8Characteristic with ReadNotify[Int] {

  override final val characteristicType = hap.characteristic.occupancyDetected

  override final def min: Int = 0
  override final def max: Int = 1

  override final def unit: Option[String] = None
}
