package com.github.otah.hap.api.services.sensor

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics.{ContactSensorStateCharacteristic, StatusActiveCharacteristic}
import com.github.otah.hap.api.services.OptionalName

trait ContactSensorService extends AccessoryService with OptionalName {

  override final val serviceId = hap.service.sensor.contact

  def contactDetected: ContactSensorStateCharacteristic
  def statusActive: Option[StatusActiveCharacteristic] = None

  override def characteristics: Seq[LowLevelCharacteristic] = Seq(contactDetected) ++ name ++ statusActive //TODO other optional characteristics
}
