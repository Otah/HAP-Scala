package com.github.otah.hap.api

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class EndServicesSpec extends FlatSpec {
  
  def stub: Nothing = throw new Exception()

  abstract class SPwr {
    def powerState: PowerStateCharacteristic = stub
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
