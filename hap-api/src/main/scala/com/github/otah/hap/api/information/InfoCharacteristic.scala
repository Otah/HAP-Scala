package com.github.otah.hap.api.information

import com.github.otah.hap.api.HapType
import com.github.otah.hap.api.characteristics.StringCharacteristic

import scala.concurrent.Future

trait InfoCharacteristic extends StringCharacteristic {

  def information: String

  override val reader = Reader(Future.successful(information))

  override final val writer = None
  override final val notifier = None
}

object InfoCharacteristic {

  import com.github.otah.hap.api.{HapTypes => hap}

  private case class Instance(characteristicType: HapType, information: String) extends InfoCharacteristic

  def manufacturer(manufacturer: String): InfoCharacteristic =
    Instance(hap.characteristic.manufacturer, manufacturer)

  def model(model: String): InfoCharacteristic =
    Instance(hap.characteristic.model, model)

  def serialNumber(serial: String): InfoCharacteristic =
    Instance(hap.characteristic.serialNumber, serial)
}
