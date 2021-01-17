package com.github.otah.hap.api

case class InstanceId(value: Int) extends InstanceId.Ops {
  require(value > 0, "Instance ID has to be a positive number")

  def asInstanceId: InstanceId = this

  def ->?[O](maybeObj: Option[O]): Option[Identified[O]] = maybeObj map identifying

  def +(addition: Int) = InstanceId(value + addition)
}

object InstanceId {

  trait Ops {

    def asInstanceId: InstanceId

    def identifying[O](obj: O): Identified[O] = asInstanceId -> obj

    def -->[O](obj: O): Identified[O] = identifying(obj)

    def -->?[O](maybeObj: Option[O]): Option[Identified[O]] = maybeObj map identifying
  }
}
