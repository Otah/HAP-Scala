package com.github.otah.hap.api.services

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._

trait FanService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.fan

  def powerState: Required[PowerStateCharacteristic]

  def rotationSpeed: Optional[RotationSpeedCharacteristic]

  override lazy val characteristics = Characteristics(name, powerState, rotationSpeed)
}
