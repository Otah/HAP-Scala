package com.github.otah.hap.api.services

import com.github.otah.hap.api.characteristics.PowerStateCharacteristic

trait SwitchService extends SpecializedService with OptionalName {

  override final val serviceType = hap.service.switch

  def powerState: Required[PowerStateCharacteristic]

  override def all: AllSupported = AllSupported(name, powerState)
}
