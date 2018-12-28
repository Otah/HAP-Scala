package com.github.otah.hap.api

trait LightbulbService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.lightbulb

  def powerState: PowerStateCharacteristic

  def brightness: Option[BrightnessCharacteristic]

  def colorTemperature: Option[ColorTemperatureCharacteristic] = None

  lazy val characteristics: Characteristics = Seq(powerState) ++ brightness ++ colorTemperature ++ name
}
