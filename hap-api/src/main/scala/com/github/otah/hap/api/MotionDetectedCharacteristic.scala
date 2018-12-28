package com.github.otah.hap.api

import internal._

trait MotionDetectedCharacteristic extends BoolCharacteristic with ReadNotify[Boolean] {

  override final val characteristicType = hap.characteristic.motionDetected

  override def description = "Motion detected"
}
