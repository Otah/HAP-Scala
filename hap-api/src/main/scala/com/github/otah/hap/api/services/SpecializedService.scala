package com.github.otah.hap.api.services

import com.github.otah.hap.api._

trait SpecializedService extends AccessoryService {

  type Required[+A <: LowLevelCharacteristic]
  type Optional[+A <: LowLevelCharacteristic] = Option[Required[A]]
  type AllSupported = Seq[Option[Required[LowLevelCharacteristic]]] //TODO rename Options to something more specific

  def AllSupported(opts: Option[Required[LowLevelCharacteristic]]*): AllSupported = opts

  def all: AllSupported
}
