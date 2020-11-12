package com.github.otah.hap.api.services

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics.PowerStateCharacteristic

trait SwitchService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.switch

  def powerState: Required[PowerStateCharacteristic]

  lazy val characteristics = Characteristics(name, powerState)
}
