package com.github.otah.hap.api.characteristics

import scala.concurrent.Future

trait NameCharacteristic extends StringCharacteristic {

  override final val characteristicType = hap.characteristic.name

  override final def isReadable: Boolean = true
  override final def isWritable: Boolean = false
  override final def hasEvents: Boolean = false
}

object NameCharacteristic {

  case class Concrete(name: String, override val maxLength: Option[Int] = None) extends NameCharacteristic
}
