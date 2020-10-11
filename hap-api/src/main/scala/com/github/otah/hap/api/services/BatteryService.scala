package com.github.otah.hap.api.services

import com.github.otah.hap.api.AccessoryService
import com.github.otah.hap.api.characteristics._

trait BatteryService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.battery

  def battery: BatteryCharacteristic

  def statusLowBattery: StatusLowBatteryCharacteristic

  def chargingState: ChargingStateCharacteristic

  override lazy val characteristics: Characteristics = Seq(battery, statusLowBattery, chargingState) ++ name
}
