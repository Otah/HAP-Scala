package com.github.otah.hap.api.characteristics

import scala.concurrent.Future

trait NameCharacteristic extends StringCharacteristic {

  override final val characteristicType = hap.characteristic.name

  def name: String
  override val reader = Reader(Future.successful(name))

  override final def writer = None
  override final def notifier = None
}

object NameCharacteristic {
  private case class NameCharacteristicClass(name: String) extends NameCharacteristic
  def apply(name: String): NameCharacteristic = NameCharacteristicClass(name)
  def apply(name: String, maxLength: Int): NameCharacteristic = NameCharacteristicClass(name)
}
