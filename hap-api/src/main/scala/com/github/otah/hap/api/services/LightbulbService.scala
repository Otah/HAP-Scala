package com.github.otah.hap.api.services

import com.github.otah.hap.api.AccessoryService
import com.github.otah.hap.api.characteristics._

trait LightbulbService extends AccessoryService with OptionalName with Has6Characteristics {

  override final val serviceType = hap.service.lightbulb

  def powerState: PowerStateCharacteristic

  def brightness: Option[BrightnessCharacteristic]

  def colorTemperature: Option[ColorTemperatureCharacteristic] = None

  lazy val characteristics = Characteristics(
    id1 -> name,
    id2 -> powerState,
    id3 -> brightness,
    id4 -> colorTemperature,
  )
}
