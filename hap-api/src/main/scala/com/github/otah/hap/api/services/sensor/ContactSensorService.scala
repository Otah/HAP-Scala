package com.github.otah.hap.api.services.sensor

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._
import com.github.otah.hap.api.services._

trait ContactSensorService extends AccessoryService with OptionalName with Has6Characteristics {

  override final val serviceId = hap.service.sensor.contact

  def contactDetected: ContactSensorStateCharacteristic
  def statusActive: Option[StatusActiveCharacteristic] = None

  //TODO other optional characteristics
  override def characteristics: Characteristics = Seq(
    id1 - name,
    id2 - contactDetected,
    id3 - statusActive,
  )
}
