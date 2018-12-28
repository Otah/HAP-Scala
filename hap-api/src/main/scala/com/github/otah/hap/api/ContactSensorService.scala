package com.github.otah.hap.api

trait ContactSensorService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.sensor.contact

  def contactDetected: ContactSensorStateCharacteristic
  def statusActive: Option[StatusActiveCharacteristic] = None

  override def characteristics: Characteristics = Seq(contactDetected) ++ name ++ statusActive //TODO other optional characteristics
}
