package com.github.otah.hap.api.characteristics

trait PositionStateCharacteristic extends UInt8Characteristic with ReadNotify with Units.None {

  override final val characteristicType = hap.characteristic.position.state

  override def min: Int = 0
  override def max: Int = 2
}

object PositionStateCharacteristic {

  val goingToMinimum = 0
  val goingToMaximum = 1
  val stopped = 2
}
