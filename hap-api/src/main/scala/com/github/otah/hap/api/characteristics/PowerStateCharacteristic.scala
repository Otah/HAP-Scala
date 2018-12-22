package com.github.otah.hap.api.characteristics

trait PowerStateCharacteristic extends BoolCharacteristic with ReadWriteNotify[Boolean] {

  override final val characteristicId = hap.characteristic.powerState

  override def description = "Turn on and off"
}
