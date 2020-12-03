package com.github.otah.hap.api.services.experimental.sensor

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._
import com.github.otah.hap.api.services._
import experimental._

trait MotionSensorService extends AccessoryService with experimental.OptionalName with Has6Characteristics {

  override final val serviceType = hap.service.sensor.motion

  def motionDetected: MotionDetectedCharacteristic
  def statusActive: Option[StatusActiveCharacteristic] = None

  //TODO other optional characteristics
  lazy val characteristics = Characteristics(
    id1 ->? name,
    id2 -> motionDetected,
    id3 ->? statusActive,
  )
}
