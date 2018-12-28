package com.github.otah.hap.api

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

import scala.concurrent.Future

@RunWith(classOf[JUnitRunner])
class EndCharacteristicsSpec extends FlatSpec {

  def stub: Some[Nothing] = throw new Exception()

  trait ReadOnly {
    def reader = stub
    def notifier = stub
  }

  trait ReadWrite extends ReadOnly {
    def writer = stub
  }

  "End-user read-only characteristics" should "require only reader and notifier defined" in {

    abstract class X extends ReadOnly

    new X with ContactSensorStateCharacteristic

    new X with MotionDetectedCharacteristic

    new X with OutletInUseCharacteristic

    new X with StatusActiveCharacteristic
  }

  "End-user read-write characteristics" should "require only reader, writer and notifier defined" in {

    abstract class X extends ReadWrite

    new X with BrightnessCharacteristic

    new X with ColorTemperatureCharacteristic

    new X with MuteCharacteristic

    new X with PowerStateCharacteristic

    new X with RotationSpeedCharacteristic

    new X with VolumeCharacteristic
  }

  "Name characteristic" should "require name and maxLength provided" in {
    new NameCharacteristic {
      def name = "whatever name"
      def maxLength = 10
    }
  }

  "Programmable switch event" should "require notifier, max and pressed SPI" in {
    new ProgrammableSwitchEvent {
      override def notifier = stub
      override def max = 1
      override protected def pressed(pressId: Int) = Future.successful(()) // Future.unit since 2.12
    }
  }
}
