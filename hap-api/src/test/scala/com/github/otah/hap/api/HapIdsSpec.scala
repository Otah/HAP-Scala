package com.github.otah.hap.api

import com.github.otah.hap.api.HapIds._
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class HapIdsSpec extends FlatSpec with Matchers {

  object expected {
    val rotSpeed  = "00000029-0000-1000-8000-0026BB765291"
    val volume    = "00000119-0000-1000-8000-0026BB765291"
    val mute      = "0000011A-0000-1000-8000-0026BB765291"
    val colorTemp = "000000CE-0000-1000-8000-0026BB765291"
  }

  def fullId(f: characteristic.type => HapId) = f(characteristic).asFullUUID

  "Rotation speed" should "have two digits with 6-zeros prefix" in {
    fullId(_.rotationSpeed) shouldBe expected.rotSpeed
  }

  "Volume" should "have three hexadecimal digits with 5-zeros prefix" in {
    fullId(_.volume) shouldBe expected.volume
  }

  "Mute" should "have three hexadecimal digits with 5-zeros prefix" in {
    fullId(_.mute) shouldBe expected.mute
  }

  "Color temperature" should "have two hexadecimal digits with 6-zeros prefix" in {
    fullId(_.colorTemperature) shouldBe expected.colorTemp
  }
}
