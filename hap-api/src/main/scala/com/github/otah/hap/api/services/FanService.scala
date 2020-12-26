package com.github.otah.hap.api.services

import com.github.otah.hap.api.characteristics._

trait FanService extends HigherKindService with OptionalName {

  override final val serviceType = hap.service.fan

  def powerState: Required[PowerStateCharacteristic]

  def rotationSpeed: Optional[RotationSpeedCharacteristic]

  override def options: Options = Options(name, powerState, rotationSpeed)
}
