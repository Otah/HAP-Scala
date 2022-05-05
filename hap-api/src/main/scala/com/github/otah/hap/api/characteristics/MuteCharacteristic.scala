package com.github.otah.hap.api.characteristics

trait MuteCharacteristic extends BoolCharacteristic with ReadWriteNotify[Boolean] {

  override final val characteristicType = hap.characteristic.mute
}
