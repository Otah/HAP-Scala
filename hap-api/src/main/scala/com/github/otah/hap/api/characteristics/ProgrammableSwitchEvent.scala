package com.github.otah.hap.api.characteristics

import scala.concurrent.Future

trait ProgrammableSwitchEvent extends UInt8Characteristic with Units.None {

  override def min = 0

  override final val characteristicType = hap.characteristic.programmableSwitchEvent

  override final def isReadable: Boolean = true
  override final def isWritable: Boolean = false
  override final def hasEvents: Boolean = true
}

object ProgrammableSwitchEvent {

  object Press {
    val single = 0
    val double = 1
    val long = 2
  }

  trait Convenience extends ProgrammableSwitchEvent {
    protected def pressed(pressId: Int): Future[Unit]
  }

  import Press._

  trait SinglePress extends Convenience {
    override def max = single
    def singlePressed() = pressed(single)
  }

  trait SingleOrDoublePress extends SinglePress {
    override def max = double
    def doublePressed() = pressed(double)
  }

  trait AllPressTypes extends SingleOrDoublePress {
    override def max = long
    def longPressed() = pressed(long)
  }
}
