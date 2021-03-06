package com.github.otah.hap.api.services

import com.github.otah.hap.api.AccessoryService
import com.github.otah.hap.api.characteristics.PowerStateCharacteristic

trait SwitchService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.switch

  def powerState: PowerStateCharacteristic

  lazy val characteristics: Characteristics = Seq(powerState) ++ name
}
