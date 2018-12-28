package com.github.otah.hap.api

import internal._

trait VolumeCharacteristic extends UInt8Characteristic with IntegerPercentages with ReadWriteNotify[Int] {

  override final val characteristicType = hap.characteristic.volume

  override def description = "Volume level"
}
