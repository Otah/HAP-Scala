package com.github.otah.hap.api.services
import com.github.otah.hap.api.*
import com.github.otah.hap.api.characteristics.*
import spray.json.JsValue

import scala.concurrent.{ExecutionContext, Future}

class CompileCheckOfCreation {

  trait MockedLight extends LightbulbService {
    override def iid: InstanceId = ???
    override def characteristicsWrite(updates: Map[InstanceId, JsValue])(implicit ec: ExecutionContext): Seq[Future[_]] = ???
    override def characteristicsValues()(implicit ec: ExecutionContext): Map[InstanceId, Future[JsValue]] = ???
    override def characteristicsValues(ids: Set[InstanceId])(implicit ec: ExecutionContext): Map[InstanceId, Future[JsValue]] = ???
    override def characteristicsSubscribe(callback: Map[InstanceId, JsValue] => Unit): Subscription = ???
  }

  class LightAutomatic extends MockedLight {

    override def powerState: Required[PowerStateCharacteristic] = new A with PowerStateCharacteristic
    override def brightness: Optional[BrightnessCharacteristic] = Some(new A with BrightnessCharacteristic)
    override val colorTemperature: Optional[ColorTemperatureCharacteristic] = new A with ColorTemperatureCharacteristic
  }

  class LightExplicit extends MockedLight {

    override def powerState: Required[PowerStateCharacteristic] =
      new IID(3) with PowerStateCharacteristic

    override def brightness: Optional[BrightnessCharacteristic] = Some {
      new IID(4) with BrightnessCharacteristic
    }
    override val colorTemperature: Optional[ColorTemperatureCharacteristic] =
      new IID(5) with ColorTemperatureCharacteristic
  }
}
