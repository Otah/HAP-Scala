package com.github.otah.hap.api.services.sensor

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics.{MotionDetectedCharacteristic, StatusActiveCharacteristic}
import com.github.otah.hap.api.services.{Characteristics, OptionalName}

trait MotionSensorService extends AccessoryService with OptionalName {

  override final val serviceId = hap.service.sensor.motion

  def motionDetected: MotionDetectedCharacteristic
  def statusActive: Option[StatusActiveCharacteristic] = None

  lazy val characteristics: Characteristics = Seq(motionDetected) ++ name ++ statusActive //TODO other optional characteristics
}
