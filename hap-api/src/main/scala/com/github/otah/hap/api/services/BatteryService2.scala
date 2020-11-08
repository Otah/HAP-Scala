package com.github.otah.hap.api.services

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._

trait BatteryService2 extends AccessoryService with OptionalName2 {

  override final val serviceType = hap.service.battery

  def battery: Identified[BatteryCharacteristic]

  def statusLowBattery: Identified[StatusLowBatteryCharacteristic]

  def chargingState: Identified[ChargingStateCharacteristic]

  override lazy val characteristics = Characteristics(
    name,
    battery,
    statusLowBattery,
    chargingState,
  )
}
