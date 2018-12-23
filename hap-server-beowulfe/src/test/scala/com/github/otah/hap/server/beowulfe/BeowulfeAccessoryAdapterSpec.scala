package com.github.otah.hap.server.beowulfe

import javax.json.JsonValue

import com.beowulfe.hap.HomekitAccessory
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

import scala.collection.JavaConverters._

@RunWith(classOf[JUnitRunner])
class BeowulfeAccessoryAdapterSpec extends FlatSpec with Matchers with ImplicitExecutionContext {

  "Original accessory creation" should "give the same result as the converted custom accessory" in {

    import BeowulfeAccessoryAdapter.Implicit._

    val original: HomekitAccessory = BeowulfeSwitch(1, "The Label")
    val custom: HomekitAccessory = TestSwitch(1, "The Label")

    assert(original.getId === custom.getId)
    assert(original.getLabel === custom.getLabel)
    assert(original.getModel === custom.getModel)
    assert(original.getManufacturer === custom.getManufacturer)
    assert(original.getSerialNumber === custom.getSerialNumber)

    assert(original.getServices.size() === custom.getServices.size())

    val oService = original.getServices.iterator().next()
    val cService = custom.getServices.iterator().next()

    assert(oService.getType === "00000049-0000-1000-8000-0026BB765291")
    assert(cService.getType === "49")

    val oCharacteristics = oService.getCharacteristics
    val cCharacteristics = cService.getCharacteristics

    val oCharIt = oCharacteristics.iterator()
    val oName = oCharIt.next()
    val oSwitch = oCharIt.next()

    val cCharIt = cCharacteristics.iterator()
    val cSwitch = cCharIt.next()
    val cName = cCharIt.next()

    val oNameJson = oName.toJson(1).get()
    val cNameJson = cName.toJson(1).get()

    val oNameMap = new java.util.HashMap[String, JsonValue](oNameJson)
    val cNameMap = new java.util.HashMap[String, JsonValue](cNameJson)

    val tp = "type"
    assert(oNameJson.getString(tp) === "00000023-0000-1000-8000-0026BB765291")
    assert(cNameJson.getString(tp) === "23")
    oNameMap.remove(tp)
    cNameMap.remove(tp)

    assert(oNameMap === cNameMap)

    val oSwitchJson = oSwitch.toJson(1).get()
    val cSwitchJson = cSwitch.toJson(1).get()

    val perms = "perms"
    assert(oSwitchJson.getJsonArray(perms).asScala.toSet === cSwitchJson.getJsonArray(perms).asScala.toSet)

    assert(oSwitchJson.getString(tp) === "00000025-0000-1000-8000-0026BB765291")
    assert(cSwitchJson.getString(tp) === "25")

    val oSwitchMap = new java.util.HashMap[String, JsonValue](oSwitchJson)
    val cSwitchMap = new java.util.HashMap[String, JsonValue](cSwitchJson)

    oSwitchMap.remove(perms)
    cSwitchMap.remove(perms)
    oSwitchMap.remove(tp)
    cSwitchMap.remove(tp)
    assert(oSwitchMap === cSwitchMap)
  }
}
