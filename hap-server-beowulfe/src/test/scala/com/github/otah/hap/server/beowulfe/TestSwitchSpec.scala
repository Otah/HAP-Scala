package com.github.otah.hap.server.beowulfe

import com.github.otah.hap.api.characteristics.NameCharacteristic
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestSwitchSpec extends FlatSpec with Matchers with ImplicitExecutionContext {

  "Label of the TestSwitch" should "be used as a name of its single service" in {

    val test = TestSwitch(1, "The Label")

    val names = test.services flatMap (_.characteristics) flatMap (_.characteristic) collect {
      case name: NameCharacteristic => name
    }
    names map (_.name) shouldBe Seq("The Label")
  }
}
