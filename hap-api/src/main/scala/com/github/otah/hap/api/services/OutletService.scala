package com.github.otah.hap.api.services

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics.{OutletInUseCharacteristic, PowerStateCharacteristic}

trait OutletService extends AccessoryService with OptionalName with Has3Characteristics {

  override final val serviceType = hap.service.outlet

  def powerState: PowerStateCharacteristic
  def inUse: OutletInUseCharacteristic

  override lazy val characteristics: Characteristics = Seq(
    id1 -> name,
    id2 -> powerState,
    id3 -> inUse,
  )
}
