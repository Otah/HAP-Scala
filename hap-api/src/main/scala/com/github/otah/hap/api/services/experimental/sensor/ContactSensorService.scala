package com.github.otah.hap.api.services.experimental.sensor

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._
import com.github.otah.hap.api.services._
import experimental._

trait ContactSensorService extends AccessoryService with experimental.OptionalName with Has6Characteristics {

  override final val serviceType = hap.service.sensor.contact

  def contactDetected: ContactSensorStateCharacteristic
  def statusActive: Option[StatusActiveCharacteristic] = None

  //TODO other optional characteristics
  override def characteristics = Characteristics(
    id1 ->> name,
    id2 -> contactDetected,
    id3 ->> statusActive,
  )
}
