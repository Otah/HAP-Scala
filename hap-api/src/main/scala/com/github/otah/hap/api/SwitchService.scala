package com.github.otah.hap.api

trait SwitchService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.switch

  def powerState: PowerStateCharacteristic

  lazy val characteristics: Characteristics = Seq(powerState) ++ name
}
