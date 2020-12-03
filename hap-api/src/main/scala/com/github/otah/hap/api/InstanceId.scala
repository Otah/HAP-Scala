package com.github.otah.hap.api

case class InstanceId(value: Int) {
  require(value > 0, "Instance ID has to be a positive number")

  def identifying[O](obj: O): Identified[O] = this -> obj

  def ->?[O](maybeObj: Option[O]): Option[Identified[O]] = maybeObj map identifying

  def +(addition: Int) = InstanceId(value + addition)
}
