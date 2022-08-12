package com.github.otah.hap.api

import scala.language.implicitConversions

package object services {

  implicit def autoOption[T](x: T): Some[T] = Some(x)

  type Characteristics = Seq[Identified[Characteristic]]

  def Characteristics(chs: Option[Identified[Characteristic]]*): Characteristics = chs.flatten
}
