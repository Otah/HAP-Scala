package com.github.otah.hap.api.information

import com.github.otah.hap.api.characteristics.BoolCharacteristic

import scala.concurrent.Future

trait IdentifyCharacteristic extends BoolCharacteristic {

  override final val characteristicType = hap.characteristic.identify

  override final def isReadable: Boolean = false
  override final def isWritable: Boolean = true
  override final def hasEvents: Boolean = false
}
