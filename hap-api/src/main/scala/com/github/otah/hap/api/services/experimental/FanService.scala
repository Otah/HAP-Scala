package com.github.otah.hap.api.services.experimental

import com.github.otah.hap.api.AccessoryService
import com.github.otah.hap.api.characteristics.{PowerStateCharacteristic, RotationSpeedCharacteristic}
import com.github.otah.hap.api.services._

trait FanService extends AccessoryService with experimental.OptionalName with Has4Characteristics {

  override final val serviceType = hap.service.fan

  def powerState: PowerStateCharacteristic

  def rotationSpeed: Option[RotationSpeedCharacteristic]

  override lazy val characteristics = Characteristics(
    id1 ->> name,
    id2 -> powerState,
    id3 ->> rotationSpeed,
  )
}
