package com.github.otah.hap.api.information

import com.github.otah.hap.api.HapType
import com.github.otah.hap.api.characteristics.StringCharacteristic

import scala.concurrent.Future

trait InfoCharacteristic extends StringCharacteristic {

  override final def isReadable: Boolean = true
  override final def isWritable: Boolean = false
  override final def hasEvents: Boolean = false
}

object InfoCharacteristic {

  import com.github.otah.hap.api.{HapTypes => hap}

  private case class Instance(characteristicType: HapType, x: Any) extends InfoCharacteristic

  def manufacturer(manufacturer: String): InfoCharacteristic =
    Instance(hap.characteristic.manufacturer, manufacturer)

  def model(model: String): InfoCharacteristic =
    Instance(hap.characteristic.model, model)

  def serialNumber(serial: String): InfoCharacteristic =
    Instance(hap.characteristic.serialNumber, serial)

  def hardware(revision: Revision): InfoCharacteristic =
    Instance(hap.characteristic.hardware.revision, revision)

  def firmware(revision: Revision): InfoCharacteristic =
    Instance(hap.characteristic.firmware.revision, revision)
}
