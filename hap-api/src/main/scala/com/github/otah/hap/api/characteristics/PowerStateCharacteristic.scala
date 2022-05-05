package com.github.otah.hap.api.characteristics

trait PowerStateCharacteristic extends BoolCharacteristic with ReadWriteNotify[Boolean] {

  override final val characteristicType = hap.characteristic.powerState
}
