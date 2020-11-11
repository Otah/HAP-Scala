package com.github.otah.hap.api.services

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._

trait LightbulbService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.lightbulb

  def powerState: Identified[PowerStateCharacteristic]

  def brightness: Option[Identified[BrightnessCharacteristic]]

  def colorTemperature: Option[Identified[ColorTemperatureCharacteristic]] = None

  lazy val characteristics = Characteristics(name, powerState, brightness, colorTemperature)
}
