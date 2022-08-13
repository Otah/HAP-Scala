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

  trait Manufacturer extends InfoCharacteristic {
    override val characteristicType = hap.characteristic.manufacturer
  }

  trait Model extends InfoCharacteristic {
    override val characteristicType = hap.characteristic.model
  }

  trait SerialNumber extends InfoCharacteristic {
    override val characteristicType = hap.characteristic.serialNumber
  }
}
