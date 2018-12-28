package com.github.otah.hap.api

trait FanService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.fan

  def powerState: PowerStateCharacteristic

  def rotationSpeed: Option[RotationSpeedCharacteristic]

  override lazy val characteristics: Characteristics = Seq(powerState) ++ rotationSpeed ++ name
}
