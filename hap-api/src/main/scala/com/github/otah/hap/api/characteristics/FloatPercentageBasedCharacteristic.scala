package com.github.otah.hap.api.characteristics

trait FloatPercentageBasedCharacteristic extends FloatCharacteristic with Units.Percentage {

  override def min = 0.0f
  override def max = 100.0f
}
