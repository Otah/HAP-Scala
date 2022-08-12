package com.github.otah.hap.api.characteristics

trait CurrentRelativeHumidityCharacteristic extends FloatPercentageBasedCharacteristic with ReadNotify {

  override final val characteristicType = hap.characteristic.relativeHumidity.current
}
