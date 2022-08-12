package com.github.otah.hap.api.characteristics

trait PowerStateCharacteristic extends BoolCharacteristic with ReadWriteNotify {

  override final val characteristicType = hap.characteristic.powerState
}
