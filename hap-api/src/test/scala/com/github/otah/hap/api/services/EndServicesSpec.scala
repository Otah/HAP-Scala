package com.github.otah.hap.api.services

import com.github.otah.hap.api.Required
import com.github.otah.hap.api.characteristics.PowerStateCharacteristic
import com.github.otah.hap.api.services.sensor._
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class EndServicesSpec extends AnyFlatSpec {

  def stub: Nothing = throw new Exception()

  abstract class SPwr {
    def powerState: Required[PowerStateCharacteristic] = stub
  }

  "End-user services" should "need only their characteristics to be specified" in {

    new ContactSensorService {
      override def contactDetected = stub
    }

    new MotionSensorService {
      override def motionDetected = stub
    }

    new SPwr with SwitchService

    new SPwr with FanService {
      override def rotationSpeed = stub
    }

    new SPwr with LightbulbService {
      override def brightness = stub
    }

    new SPwr with OutletService {
      override def inUse = stub
    }

    new SpeakerService {
      override def mute = stub
      override def volume = stub
    }

    new ProgrammableSwitchService {
      override def programmableSwitchEvent = stub
    }
  }
}
