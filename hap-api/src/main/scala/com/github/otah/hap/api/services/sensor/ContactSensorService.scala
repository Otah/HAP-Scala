package com.github.otah.hap.api.services.sensor

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._
import com.github.otah.hap.api.services._

trait ContactSensorService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.sensor.contact

  def contactDetected: Required[ContactSensorStateCharacteristic]
  def statusActive: Optional[StatusActiveCharacteristic] = None

  //TODO other optional characteristics
  override def characteristics = Characteristics(name, contactDetected, statusActive)
}
