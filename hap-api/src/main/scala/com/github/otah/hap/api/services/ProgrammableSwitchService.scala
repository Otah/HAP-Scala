package com.github.otah.hap.api.services

import com.github.otah.hap.api.AccessoryService
import com.github.otah.hap.api.characteristics.ProgrammableSwitchEvent

trait ProgrammableSwitchService extends AccessoryService with OptionalName with Has3Characteristics {

  override final val serviceId = hap.service.programmableSwitch

  def programmableSwitchEvent: ProgrammableSwitchEvent

  //TODO label index
  lazy val characteristics: Characteristics = Seq(
    id1 - name,
    id2 - programmableSwitchEvent,
  )
}
