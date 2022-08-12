package com.github.otah.hap.api.characteristics

trait MotionDetectedCharacteristic extends BoolCharacteristic with ReadNotify {

  override final val characteristicType = hap.characteristic.motionDetected
}
