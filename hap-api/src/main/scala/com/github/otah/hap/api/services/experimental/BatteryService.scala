package com.github.otah.hap.api.services.experimental

import com.github.otah.hap.api.AccessoryService
import com.github.otah.hap.api.characteristics._
import com.github.otah.hap.api.services._

trait BatteryService extends AccessoryService with experimental.OptionalName with Has4Characteristics {

  override final val serviceType = hap.service.battery

  def battery: BatteryCharacteristic

  def statusLowBattery: StatusLowBatteryCharacteristic

  def chargingState: ChargingStateCharacteristic

  override lazy val characteristics = Characteristics(
    id1 ->> name,
    id2 -> battery,
    id3 -> statusLowBattery,
    id4 -> chargingState,
  )
}
