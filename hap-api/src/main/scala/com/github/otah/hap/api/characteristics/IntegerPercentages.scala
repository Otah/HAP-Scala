package com.github.otah.hap.api.characteristics

trait IntegerPercentages extends Units.Percentage {

  this: NumberCharacteristic[Int] =>

  override def min = 0
  override def max = 100
}
