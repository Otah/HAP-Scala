package com.github.otah.hap.api.services

import com.github.otah.hap.api
import com.github.otah.hap.api.{Identified, InstanceId}
import com.github.otah.hap.api.characteristics.PowerStateCharacteristic
import com.github.otah.hap.api.services.sensor._
import org.scalatest.flatspec.AnyFlatSpec
import spray.json.JsValue

import scala.concurrent.{ExecutionContext, Future}

class EndServicesSpec extends AnyFlatSpec {

  def stub: Nothing = throw new Exception()

  trait EasiestStrategy extends SpecializedService with IdStrategy.Explicit {
    override def iid: InstanceId = ???
    override def characteristicsWrite(x: Map[InstanceId, JsValue])(implicit ec: ExecutionContext): Seq[Future[_]] = ???
    override def characteristicsValues()(implicit ec: ExecutionContext): Map[InstanceId, Future[JsValue]] = ???
    override def characteristicsValues(ids: Set[InstanceId])(implicit ec: ExecutionContext): Map[InstanceId, Future[JsValue]] = ???
    override def characteristicsSubscribe(callback: Map[InstanceId, JsValue] => Unit): api.Subscription = ???
  }
  abstract class SPwr extends EasiestStrategy {
    def powerState: Identified[PowerStateCharacteristic] = stub
  }

  "End-user services" should "need only their characteristics to be specified" in {

    new ContactSensorService with EasiestStrategy {
      override def contactDetected = stub
    }

    new MotionSensorService with EasiestStrategy {
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

    new SpeakerService with EasiestStrategy {
      override def mute = stub
      override def volume = stub
    }

    new ProgrammableSwitchService with EasiestStrategy {
      override def programmableSwitchEvent = stub
    }
  }
}
