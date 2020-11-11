package com.github.otah.hap.api.services

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._

trait OutletService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.outlet

  def powerState: Required[PowerStateCharacteristic]
  def inUse: Required[OutletInUseCharacteristic]

  override lazy val characteristics = Characteristics(name, powerState, inUse)
}
