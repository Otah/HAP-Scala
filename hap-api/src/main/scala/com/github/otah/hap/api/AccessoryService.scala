package com.github.otah.hap.api

trait AccessoryService extends TypeConvenience {

  def serviceType: HapType
  def characteristics: Seq[LowLevelCharacteristic]
}
