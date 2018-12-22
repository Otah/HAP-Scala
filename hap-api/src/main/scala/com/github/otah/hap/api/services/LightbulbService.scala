package com.github.otah.hap.api.services

import com.github.otah.hap.api.AccessoryService
import com.github.otah.hap.api.characteristics._

trait LightbulbService extends AccessoryService with OptionalName {

  override final val serviceId = hap.service.lightbulb

  def powerState: PowerStateCharacteristic

  def brightness: Option[BrightnessCharacteristic]

  def colorTemperature: Option[ColorTemperatureCharacteristic] = None

  lazy val characteristics: Characteristics = Seq(powerState) ++ brightness ++ colorTemperature ++ name
}
