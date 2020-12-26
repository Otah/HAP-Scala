package com.github.otah.hap.api.services
import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._

class CompileCheckOfCreation {

  class LightAutomatic extends LightbulbService with IdStrategy.Automatic {

    override def powerState: Required[PowerStateCharacteristic] = pwr
    override def brightness: Optional[BrightnessCharacteristic] = Some(brig)
    override def colorTemperature: Optional[ColorTemperatureCharacteristic] = color

    override def baseInstanceId: Int = ???
  }

  class LightExplicit extends LightbulbService with IdStrategy.Explicit {

    override def powerState: Required[PowerStateCharacteristic] =
      3 --> pwr

    override def brightness: Optional[BrightnessCharacteristic] = Some {
      4 --> brig
    }
    override def colorTemperature: Optional[ColorTemperatureCharacteristic] =
      5 --> color
  }

  val pwr = new PowerStateCharacteristic {
    override def reader: Some[Reader] = ???
    override def writer: Some[Writer] = ???
    override def notifier: Some[Notifier] = ???
  }
  val brig = new BrightnessCharacteristic {
    override def reader: Some[Reader] = ???
    override def writer: Some[Writer] = ???
    override def notifier: Some[Notifier] = ???
  }
  val color = new ColorTemperatureCharacteristic {
    override def reader: Some[Reader] = ???
    override def writer: Some[Writer] = ???
    override def notifier: Some[Notifier] = ???
  }
}
