package com.github.otah.hap.api.characteristics

trait MuteCharacteristic extends BoolCharacteristic with ReadWriteNotify[Boolean] {

  override final val characteristicType = hap.characteristic.mute

  override def description = "Mute and un-mute"
}
