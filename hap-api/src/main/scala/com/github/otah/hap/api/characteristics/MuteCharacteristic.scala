package com.github.otah.hap.api.characteristics

trait MuteCharacteristic extends BoolCharacteristic with ReadWriteNotify[Boolean] {

  override final val characteristicId = hap.characteristic.mute

  override def description = "Mute and un-mute"
}
