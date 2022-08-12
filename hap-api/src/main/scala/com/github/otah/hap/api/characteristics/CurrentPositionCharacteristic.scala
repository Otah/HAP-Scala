package com.github.otah.hap.api.characteristics

trait CurrentPositionCharacteristic extends UInt8Characteristic with ReadNotify with IntegerPercentages {

  override final val characteristicType = hap.characteristic.position.current
}
