package com.github.otah.hap.api

import scala.language.implicitConversions

package object services {

  implicit def characteristicInstanceAutoOption(x: Identified[LowLevelCharacteristic]) = Some(x)

  type Characteristics = Seq[Identified[LowLevelCharacteristic]]

  def Characteristics(chs: Option[Identified[LowLevelCharacteristic]]*): Characteristics = chs.flatten
}
