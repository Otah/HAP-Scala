package com.github.otah.hap.api.characteristics

trait VolumeCharacteristic extends UInt8Characteristic with IntegerPercentages with ReadWriteNotify {

  override final val characteristicType = hap.characteristic.volume
}
