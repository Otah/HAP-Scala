package com.github.otah.hap.api.services

import com.github.otah.hap.api._

trait SpecializedService extends Service {

  type Required[+A <: Characteristic]
  type Optional[+A <: Characteristic] = Option[Required[A]]
  type AllSupported = Seq[Option[Required[Characteristic]]] //TODO rename Options to something more specific

  def AllSupported(opts: Option[Required[Characteristic]]*): AllSupported = opts

  def all: AllSupported

  //TODO consider non-getter name
  def getCharacteristic[A <: Characteristic](required: Required[A]): A
  def getCharacteristic[A <: Characteristic](optional: Optional[A]): Option[A]
}
