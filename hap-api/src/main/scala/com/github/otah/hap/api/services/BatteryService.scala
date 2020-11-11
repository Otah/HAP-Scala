package com.github.otah.hap.api.services

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._

trait BatteryService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.battery

  def battery: Required[BatteryCharacteristic]

  def statusLowBattery: Required[StatusLowBatteryCharacteristic]

  def chargingState: Required[ChargingStateCharacteristic]

  override lazy val characteristics = Characteristics(
    name,
    battery,
    statusLowBattery,
    chargingState,
  )
}
