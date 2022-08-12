package com.github.otah.hap.api.characteristics

trait BatteryCharacteristic extends UInt8Characteristic with IntegerPercentages with ReadNotify {

  override final val characteristicType = hap.characteristic.battery
}
