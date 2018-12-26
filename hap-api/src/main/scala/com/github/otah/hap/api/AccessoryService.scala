package com.github.otah.hap.api

trait AccessoryService extends IdConvenience {

  def serviceId: HapId
  def characteristics: Seq[CharacteristicInstance]
}
