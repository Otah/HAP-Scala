package com.github.otah.hap.api.information

import com.github.otah.hap.api.HapType
import com.github.otah.hap.api.characteristics.StringCharacteristic

import scala.concurrent.Future

trait InfoCharacteristic extends StringCharacteristic {

  override def description = "A read-only information" //TODO make None once optional

  def information: String

  override val reader = Reader(Future.successful(information))

  override final val writer = None
  override final val notifier = None
}

object InfoCharacteristic {

  import com.github.otah.hap.api.{HapTypes => hap}

  case class Instance(characteristicType: HapType, information: String, maxLength: Int = 64) extends InfoCharacteristic

  def manufacturer(manufacturer: String, maxLength: Int = 64): InfoCharacteristic =
    Instance(hap.characteristic.manufacturer, manufacturer, maxLength)

  def model(model: String, maxLength: Int = 64): InfoCharacteristic =
    Instance(hap.characteristic.model, model, maxLength)

  def serialNumber(serial: String, maxLength: Int = 64): InfoCharacteristic =
    Instance(hap.characteristic.serialNumber, serial, maxLength)
}
