package com.github.otah.hap.api.services

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics.{OutletInUseCharacteristic, PowerStateCharacteristic}

trait OutletService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.outlet

  def powerState: PowerStateCharacteristic
  def inUse: OutletInUseCharacteristic

  override lazy val characteristics: Seq[Characteristic[_]] = Seq(powerState, inUse) ++ name
}
