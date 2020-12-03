package com.github.otah.hap.api.services.experimental

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics.{OutletInUseCharacteristic, PowerStateCharacteristic}
import com.github.otah.hap.api.services._

trait OutletService extends AccessoryService with experimental.OptionalName with Has3Characteristics {

  override final val serviceType = hap.service.outlet

  def powerState: PowerStateCharacteristic
  def inUse: OutletInUseCharacteristic

  override lazy val characteristics = Characteristics(
    id1 ->? name,
    id2 -> powerState,
    id3 -> inUse,
  )
}
