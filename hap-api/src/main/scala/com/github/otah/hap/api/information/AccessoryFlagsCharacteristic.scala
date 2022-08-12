package com.github.otah.hap.api.information

import com.github.otah.hap.api.TypedNotifier
import com.github.otah.hap.api.characteristics.UInt32Characteristic

import scala.concurrent.Future

trait AccessoryFlagsCharacteristic extends UInt32Characteristic {

  override final val characteristicType = hap.characteristic.accessoryProperties

  override def min = 0x0001
  override def max = 0xFFFF

  override def unit: Option[String] = None

  override final def isReadable: Boolean = true
  override final def isWritable: Boolean = false
  override final def hasEvents: Boolean = true
}

object AccessoryFlagsCharacteristic {

  def flagsToBits(flags: AccessoryFlags): Option[Long] = {
    if (flags.requiresAdditionalSetup) Some(0x0001)
    else None
  }
}
