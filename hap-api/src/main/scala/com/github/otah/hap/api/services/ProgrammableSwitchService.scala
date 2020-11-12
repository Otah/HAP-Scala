package com.github.otah.hap.api.services

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics.ProgrammableSwitchEvent

trait ProgrammableSwitchService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.programmableSwitch

  def programmableSwitchEvent: Required[ProgrammableSwitchEvent]

  //TODO label index
  lazy val characteristics = Characteristics(name, programmableSwitchEvent)
}
