package com.github.otah.hap.api.characteristics

trait OutletInUseCharacteristic extends BoolCharacteristic with ReadNotify {

  override final val characteristicType = hap.characteristic.outletInUse
}
