package com.github.otah.hap.api.services

import com.github.otah.hap.api.AccessoryService
import com.github.otah.hap.api.characteristics.{PowerStateCharacteristic, RotationSpeedCharacteristic}

trait FanService extends AccessoryService with OptionalName {

  override final val serviceId = hap.service.fan

  def powerState: PowerStateCharacteristic

  def rotationSpeed: Option[RotationSpeedCharacteristic]

  override lazy val characteristics: Characteristics = Seq(powerState) ++ rotationSpeed ++ name
}
