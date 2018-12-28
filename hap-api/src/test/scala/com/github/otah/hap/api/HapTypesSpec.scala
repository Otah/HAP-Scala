package com.github.otah.hap.api

import com.github.otah.hap.api.internal.HapType
import com.github.otah.hap.api.internal.HapTypes._
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class HapTypesSpec extends FlatSpec with Matchers {

  object expected {
    val rotSpeed  = "00000029-0000-1000-8000-0026BB765291"
    val volume    = "00000119-0000-1000-8000-0026BB765291"
    val mute      = "0000011A-0000-1000-8000-0026BB765291"
    val colorTemp = "000000CE-0000-1000-8000-0026BB765291"
  }

  def fullType(f: characteristic.type => HapType) = f(characteristic).asFullUUID

  "Rotation speed" should "have two digits with 6-zeros prefix" in {
    fullType(_.rotationSpeed) shouldBe expected.rotSpeed
  }

  "Volume" should "have three hexadecimal digits with 5-zeros prefix" in {
    fullType(_.volume) shouldBe expected.volume
  }

  "Mute" should "have three hexadecimal digits with 5-zeros prefix" in {
    fullType(_.mute) shouldBe expected.mute
  }

  "Color temperature" should "have two hexadecimal digits with 6-zeros prefix" in {
    fullType(_.colorTemperature) shouldBe expected.colorTemp
  }
}
