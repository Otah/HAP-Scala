package com.github.otah.hap.api.information

import com.github.otah.hap.api.TypedNotifier
import com.github.otah.hap.api.characteristics.UInt32Characteristic

import scala.concurrent.Future

trait AccessoryFlagsCharacteristic extends UInt32Characteristic {

  override final val characteristicType = hap.characteristic.accessoryProperties

  override def min = 0x0001
  override def max = 0xFFFF

  override def unit: Option[String] = None

  override def reader: Some[Reader]
  override def notifier: Some[Notifier]

  override final val writer = None
}

object AccessoryFlagsCharacteristic {

  def flagsToBits(flags: AccessoryFlags): Option[Long] = {
    if (flags.requiresAdditionalSetup) Some(0x0001)
    else None
  }

  def fixed(flags: AccessoryFlags): Option[AccessoryFlagsCharacteristic] =
    flagsToBits(flags) map Future.successful map { bits =>
      new AccessoryFlagsCharacteristic {

        override def reader = Reader(bits)

        override def notifier = Some {
          new TypedNotifier[Long] {
            override def subscribe(callback: Long => Future[Unit]) = () => ()
          }
        }
      }
    }
}
