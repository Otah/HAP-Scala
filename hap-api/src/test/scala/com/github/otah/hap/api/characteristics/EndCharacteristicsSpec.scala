package com.github.otah.hap.api.characteristics

import org.scalatest.flatspec.AnyFlatSpec

class EndCharacteristicsSpec extends AnyFlatSpec {

  "End-user read-only characteristics" should "require only reader and notifier defined" in {

    abstract class X

    new X with ContactSensorStateCharacteristic

    new X with MotionDetectedCharacteristic

    new X with OutletInUseCharacteristic

    new X with StatusActiveCharacteristic
  }

  "End-user read-write characteristics" should "require only reader, writer and notifier defined" in {

    abstract class X

    new X with BrightnessCharacteristic

    new X with ColorTemperatureCharacteristic

    new X with MuteCharacteristic

    new X with PowerStateCharacteristic

    new X with RotationSpeedCharacteristic

    new X with VolumeCharacteristic
  }

  "Name characteristic" should "require name provided" in {
    new NameCharacteristic {
      def name = "whatever name"
    }
  }

  "Programmable switch event" should "require notifier, max and pressed SPI" in {
    new ProgrammableSwitchEvent {
      override def max = 1
    }
  }
}
