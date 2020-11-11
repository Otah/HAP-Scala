package com.github.otah.hap.api.services.sensor

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._
import com.github.otah.hap.api.services._

trait MotionSensorService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.sensor.motion

  def motionDetected: Required[MotionDetectedCharacteristic]
  def statusActive: Optional[StatusActiveCharacteristic] = None

  //TODO other optional characteristics
  lazy val characteristics = Characteristics(name, motionDetected, statusActive)
}
