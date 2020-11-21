package com.github.otah.hap.api.characteristics

trait BatteryCharacteristic extends UInt8Characteristic with IntegerPercentages with ReadNotify[Int] {

  override final val characteristicType = hap.characteristic.battery
}
