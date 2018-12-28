package com.github.otah.hap.api

import internal._

trait AccessoryService extends TypeConvenience {

  def serviceType: HapType
  def characteristics: Seq[LowLevelCharacteristic]
}
