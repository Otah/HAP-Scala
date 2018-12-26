package com.github.otah.hap.api.characteristics

trait StatusActiveCharacteristic extends BoolCharacteristic with ReadNotify[Boolean] {

  override final val characteristicType = hap.characteristic.statusActive

  override def description = "Is service active"
}
