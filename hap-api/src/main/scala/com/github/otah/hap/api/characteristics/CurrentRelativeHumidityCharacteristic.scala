package com.github.otah.hap.api.characteristics

trait CurrentRelativeHumidityCharacteristic extends FloatPercentageBasedCharacteristic with ReadNotify[Float] {

  override final val characteristicType = hap.characteristic.relativeHumidity.current

  override def description = "Relative humidity"
}
