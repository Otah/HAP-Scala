package com.github.otah.hap.api

import com.github.otah.hap.api.internal.StringCharacteristic

import scala.concurrent.Future

trait NameCharacteristic extends StringCharacteristic {

  override final val characteristicType = hap.characteristic.name

  override def description = "Name of the accessory"

  def name: String
  override val reader = Reader(Future.successful(name))

  override def writer = None
  override def notifier = None
}

object NameCharacteristic {
  private case class NameCharacteristicClass(name: String, maxLength: Int) extends NameCharacteristic
  def apply(name: String): NameCharacteristic = NameCharacteristicClass(name, maxLength = 64)
  def apply(name: String, maxLength: Int): NameCharacteristic = NameCharacteristicClass(name, maxLength)
}
