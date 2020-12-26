package com.github.otah.hap.api

case class InstanceId(value: Int) extends InstanceId.Ops {
  require(value > 0, "Instance ID has to be a positive number")

  def identifying[O](obj: O): Identified[O] = this -> obj

  def ->?[O](maybeObj: Option[O]): Option[Identified[O]] = maybeObj map identifying

  def +(addition: Int) = InstanceId(value + addition)
}

object InstanceId {

  trait Ops {

    def identifying[O](obj: O): Identified[O]

    def -->[O](obj: O): Identified[O] = identifying(obj)

    def -->?[O](maybeObj: Option[O]): Option[Identified[O]] = maybeObj map identifying
  }
}
