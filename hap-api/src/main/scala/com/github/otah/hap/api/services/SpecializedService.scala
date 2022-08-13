package com.github.otah.hap.api.services

import com.github.otah.hap.api._

trait SpecializedService extends Service {
  service =>

  type Required[+A <: Characteristic] = A
  type Optional[+A <: Characteristic] = Option[Required[A]]
  type AllSupported = Seq[Option[Required[Characteristic]]]

  def AllSupported(opts: Option[Required[Characteristic]]*): AllSupported = opts

  def all: AllSupported

  trait A {
    this: Characteristic =>

    override lazy val iid: InstanceId = {
      val maybeId = all.zipWithIndex collectFirst {
        case (Some(ch), index) if ch == this => service.iid + index + 1
      }
      maybeId getOrElse {
        throw new IllegalStateException(s"$this wasn't found among characteristics. Make sure all of them are vals")
      }
    }
  }

  abstract class IID(override val iid: InstanceId) {
    this: Characteristic =>

    def this(iid: Int) = this(InstanceId(iid))
  }

  override def characteristics: Seq[Characteristic] = {
    val result: Seq[Characteristic] = all.flatten

    val (auto, explicit) = result partition (_.isInstanceOf[A])
    if (auto.nonEmpty && explicit.nonEmpty)
      throw new IllegalStateException(
        s"$this has a mixture of automatic and explicit IIDs which is not allowed. Auto: $auto Explicit: $explicit"
      )

    result
  }
}
