package com.github.otah.hap.api.characteristics

trait ContactSensorStateCharacteristic extends UInt8Characteristic with ReadNotify with Units.None {

  override final val characteristicType = hap.characteristic.contactState

  override final val min = 0
  override final val max = 1
}
