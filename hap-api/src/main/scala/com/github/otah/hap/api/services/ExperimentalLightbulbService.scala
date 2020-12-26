package com.github.otah.hap.api.services

import com.github.otah.hap.api.characteristics._

trait ExperimentalLightbulbService extends HigherKindService {

  override final val serviceType = hap.service.lightbulb

  def powerState: Req[PowerStateCharacteristic]

  def brightness: Opt[BrightnessCharacteristic]

  def colorTemperature: Opt[ColorTemperatureCharacteristic] = None

  override def options: Options = Seq(
    powerState, brightness, colorTemperature,
  )
}
