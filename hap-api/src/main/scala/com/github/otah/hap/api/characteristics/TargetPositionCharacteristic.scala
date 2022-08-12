package com.github.otah.hap.api.characteristics

trait TargetPositionCharacteristic extends UInt8Characteristic with ReadWriteNotify with IntegerPercentages {

  override final val characteristicType = hap.characteristic.position.target
}
