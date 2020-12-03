package com.github.otah.hap.api.services.experimental

import com.github.otah.hap.api.AccessoryService
import com.github.otah.hap.api.characteristics.ProgrammableSwitchEvent
import com.github.otah.hap.api.services._

trait ProgrammableSwitchService extends AccessoryService with experimental.OptionalName with Has3Characteristics {

  override final val serviceType = hap.service.programmableSwitch

  def programmableSwitchEvent: ProgrammableSwitchEvent

  //TODO label index
  lazy val characteristics = Characteristics(
    id1 ->? name,
    id2 -> programmableSwitchEvent,
  )
}
