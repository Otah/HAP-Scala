package com.github.otah.hap.api.services

import com.github.otah.hap.api.characteristics._

trait LightbulbService extends SpecializedService with OptionalName {

  override final val serviceType = hap.service.lightbulb

  def powerState: Required[PowerStateCharacteristic]

  def brightness: Optional[BrightnessCharacteristic]

  def colorTemperature: Optional[ColorTemperatureCharacteristic] = None

  def hue: Optional[HueCharacteristic] = None

  def saturation: Optional[SaturationCharacteristic] = None

  override def all: AllSupported = AllSupported(name, powerState, brightness, colorTemperature, hue, saturation)
}
