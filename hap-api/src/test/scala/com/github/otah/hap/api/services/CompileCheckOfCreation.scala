package com.github.otah.hap.api.services
import com.github.otah.hap.api.*
import com.github.otah.hap.api.characteristics.*
import spray.json.JsValue

import scala.concurrent.{ExecutionContext, Future}

class CompileCheckOfCreation {

  trait MockedLight extends LightbulbService {
    override def iid: InstanceId = ???
    override def characteristicsWrite(x: Map[InstanceId, JsValue])(implicit ec: ExecutionContext): Seq[Future[_]] = ???
    override def characteristicsValues()(implicit ec: ExecutionContext): Map[InstanceId, Future[JsValue]] = ???
    override def characteristicsValues(ids: Set[InstanceId])(implicit ec: ExecutionContext): Map[InstanceId, Future[JsValue]] = ???
    override def characteristicsSubscribe(callback: Map[InstanceId, JsValue] => Unit): Subscription = ???
  }

  class LightAutomatic extends MockedLight with IdStrategy.Automatic {

    override def powerState: Required[PowerStateCharacteristic] = pwr
    override def brightness: Optional[BrightnessCharacteristic] = Some(brig)
    override def colorTemperature: Optional[ColorTemperatureCharacteristic] = color
  }

  class LightExplicit extends MockedLight with IdStrategy.Explicit {

    override def powerState: Required[PowerStateCharacteristic] =
      3 --> pwr

    override def brightness: Optional[BrightnessCharacteristic] = Some {
      4 --> brig
    }
    override def colorTemperature: Optional[ColorTemperatureCharacteristic] =
      5 --> color
  }

  val pwr = new PowerStateCharacteristic {}
  val brig = new BrightnessCharacteristic {}
  val color = new ColorTemperatureCharacteristic {}
}
