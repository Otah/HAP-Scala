package com.github.otah.hap.api.characteristics

trait IntCharacteristic extends IntegerBasedCharacteristic {

  override final val format = "int"

  override protected final val intBounds = FormatMeta(Int.MinValue, Int.MaxValue)
}
