package com.github.otah.hap.api

import internal._

trait PowerStateCharacteristic extends BoolCharacteristic with ReadWriteNotify[Boolean] {

  override final val characteristicType = hap.characteristic.powerState

  override def description = "Turn on and off"
}
