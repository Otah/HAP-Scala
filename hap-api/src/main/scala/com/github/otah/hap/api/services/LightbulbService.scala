package com.github.otah.hap.api.services

import com.github.otah.hap.api.characteristics._

trait LightbulbService extends HigherKindService with OptionalName {

  override final val serviceType = hap.service.lightbulb

  def powerState: Required[PowerStateCharacteristic]

  def brightness: Optional[BrightnessCharacteristic]

  def colorTemperature: Optional[ColorTemperatureCharacteristic] = None

  override def options: Options = Options(name, powerState, brightness, colorTemperature)
}
