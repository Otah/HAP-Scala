package com.github.otah.hap.api.services.sensor

import com.github.otah.hap.api.characteristics._
import com.github.otah.hap.api.services._

trait MotionSensorService extends SpecializedService with OptionalName {

  override final val serviceType = hap.service.sensor.motion

  def motionDetected: Required[MotionDetectedCharacteristic]
  def statusActive: Optional[StatusActiveCharacteristic] = None

  //TODO other optional characteristics
  override def all: AllSupported = AllSupported(name, motionDetected, statusActive)
}
