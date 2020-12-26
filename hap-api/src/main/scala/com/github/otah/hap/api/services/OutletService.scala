package com.github.otah.hap.api.services

import com.github.otah.hap.api.characteristics._

trait OutletService extends HigherKindService with OptionalName {

  override final val serviceType = hap.service.outlet

  def powerState: Required[PowerStateCharacteristic]
  def inUse: Required[OutletInUseCharacteristic]

  override def options: Options = Options(name, powerState, inUse)
}
