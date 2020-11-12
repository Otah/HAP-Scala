package com.github.otah.hap.api

case class InstanceId(value: Int) {
  require(value > 0, "IID has to be positive number")

  def ->>[CH <: LowLevelCharacteristic](maybeCh: Option[CH]): Option[Identified[CH]] = maybeCh map (this -> _)

  def <=>[O](obj: O): Identified[O] = this -> obj

  def +(addition: Int) = InstanceId(value + addition)
}
