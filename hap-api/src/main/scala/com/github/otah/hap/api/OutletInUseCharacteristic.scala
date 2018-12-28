package com.github.otah.hap.api

import internal._

trait OutletInUseCharacteristic extends BoolCharacteristic with ReadNotify[Boolean] {

  override final val characteristicType = hap.characteristic.outletInUse

  override def description = "Is the device plugged in the outlet"
}
