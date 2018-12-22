package com.github.otah.hap.api.characteristics

trait VolumeCharacteristic extends UInt8Characteristic with IntegerPercentages with ReadWriteNotify[Int] {

  override final val characteristicId = hap.characteristic.volume

  override def description = "Volume level"
}
