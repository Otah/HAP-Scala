package com.github.otah.hap.api.internal

trait UInt16Characteristic extends IntegerBasedCharacteristic {

  override final val format = "uint16"

  override protected final val intBounds = FormatMeta(min = 0, max = 65535)
}
