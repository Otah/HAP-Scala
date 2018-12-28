package com.github.otah.hap.api

trait OutletService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.outlet

  def powerState: PowerStateCharacteristic
  def inUse: OutletInUseCharacteristic

  override lazy val characteristics: Characteristics = Seq(powerState, inUse) ++ name
}
