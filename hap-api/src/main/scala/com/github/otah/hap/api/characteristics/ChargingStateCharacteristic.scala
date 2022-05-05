package com.github.otah.hap.api.characteristics

trait ChargingStateCharacteristic extends UInt8Characteristic with ReadNotify[Int] with Units.None {

  override final val characteristicType = hap.characteristic.chargingState

  override def min = 0
  override def max = 2
}

object ChargingStateCharacteristic {

  val notCharging = 0
  val charging = 1
  val notChargeable = 2
}
