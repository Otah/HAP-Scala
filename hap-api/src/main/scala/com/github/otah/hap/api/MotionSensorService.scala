package com.github.otah.hap.api

trait MotionSensorService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.sensor.motion

  def motionDetected: MotionDetectedCharacteristic
  def statusActive: Option[StatusActiveCharacteristic] = None

  lazy val characteristics: Characteristics = Seq(motionDetected) ++ name ++ statusActive //TODO other optional characteristics
}
