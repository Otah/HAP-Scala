package com.github.otah.hap.server.beowulfe

import javax.json.JsonValue

import com.github.otah.hap.api._
import io.github.hapjava.accessories.HomekitAccessory
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.junit.JUnitRunner

import scala.jdk.CollectionConverters._

@RunWith(classOf[JUnitRunner])
class BeowulfeAccessoryAdapterSpec extends AnyFlatSpec with Matchers with ImplicitExecutionContext {

  "Original accessory creation" should "give the same result as the converted custom accessory" in {

    import BeowulfeAccessoryAdapter.Implicit._

    val original: HomekitAccessory = BeowulfeSwitch(1, "The Label")
    val custom: HomekitAccessory = 1 --> TestSwitch("The Label")

    assert(original.getId === custom.getId)
    assert(original.getName.get() === custom.getName.get())
    assert(original.getModel.get() === custom.getModel.get())
    assert(original.getManufacturer.get() === custom.getManufacturer.get())
    assert(original.getSerialNumber.get() === custom.getSerialNumber.get())

    assert(original.getServices.size() === custom.getServices.size())

    val oService = original.getServices.iterator().next()
    val cService = custom.getServices.iterator().next()

    assert(oService.getType === "00000049-0000-1000-8000-0026BB765291")
    assert(cService.getType === "49")

    val oCharacteristics = oService.getCharacteristics
    val cCharacteristics = cService.getCharacteristics

    val oCharIt = oCharacteristics.iterator()
    val oSwitch = oCharIt.next()

    val cCharIt = cCharacteristics.iterator()
    val cSwitch = cCharIt.next()

    val oSwitchJson = oSwitch.toJson(1).get()
    val cSwitchJson = cSwitch.toJson(1).get()

    val perms = "perms"
    assert(oSwitchJson.getJsonArray(perms).asScala.toSet === cSwitchJson.getJsonArray(perms).asScala.toSet)

    val oSwitchMap = new java.util.HashMap[String, JsonValue](oSwitchJson)
    val cSwitchMap = new java.util.HashMap[String, JsonValue](cSwitchJson)

    oSwitchMap.remove(perms)
    cSwitchMap.remove(perms)
    assert(oSwitchMap === cSwitchMap)
  }
}
