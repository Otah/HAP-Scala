package com.github.otah.hap.api.services

import com.github.otah.hap.api.characteristics.PowerStateCharacteristic
import com.github.otah.hap.api.services.experimental._
import com.github.otah.hap.api.services.experimental.sensor._
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class EndServicesSpec extends AnyFlatSpec {

  trait MockIds extends SequenceInstanceIds {
    override def baseInstanceId: Int = 10
  }
  
  def stub: Nothing = throw new Exception()

  abstract class SPwr extends MockIds {
    def powerState: PowerStateCharacteristic = stub
  }

  "End-user services" should "need only their characteristics to be specified" in {

    new ContactSensorService with MockIds {
      override def contactDetected = stub
    }

    new MotionSensorService with MockIds {
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

    new SpeakerService with MockIds {
      override def mute = stub
      override def volume = stub
    }

    new ProgrammableSwitchService with MockIds {
      override def programmableSwitchEvent = stub
    }
  }
}
