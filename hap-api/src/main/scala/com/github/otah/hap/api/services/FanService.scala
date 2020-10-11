package com.github.otah.hap.api.services

import com.github.otah.hap.api.AccessoryService
import com.github.otah.hap.api.characteristics.{PowerStateCharacteristic, RotationSpeedCharacteristic}

trait FanService extends AccessoryService with OptionalName with Has4Characteristics {

  override final val serviceType = hap.service.fan

  def powerState: PowerStateCharacteristic

  def rotationSpeed: Option[RotationSpeedCharacteristic]

  override lazy val characteristics = Characteristics(
    id1 -> name,
    id2 -> powerState,
    id3 -> rotationSpeed,
  )
}
