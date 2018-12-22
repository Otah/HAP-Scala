package com.github.otah.hap.api.characteristics

trait UInt8Characteristic extends IntegerBasedCharacteristic {

  override final val format = "uint8"

  override protected final val intBounds = FormatMeta(min = 0, max = 255)
}
