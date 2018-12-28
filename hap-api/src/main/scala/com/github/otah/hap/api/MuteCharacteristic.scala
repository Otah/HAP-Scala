package com.github.otah.hap.api

import internal._

trait MuteCharacteristic extends BoolCharacteristic with ReadWriteNotify[Boolean] {

  override final val characteristicType = hap.characteristic.mute

  override def description = "Mute and un-mute"
}
