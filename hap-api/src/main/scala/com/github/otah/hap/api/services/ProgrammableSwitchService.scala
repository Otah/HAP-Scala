package com.github.otah.hap.api.services

import com.github.otah.hap.api.characteristics.ProgrammableSwitchEvent

trait ProgrammableSwitchService extends SpecializedService with OptionalName {

  override final val serviceType = hap.service.programmableSwitch

  def programmableSwitchEvent: Required[ProgrammableSwitchEvent]

  //TODO label index
  override def all: AllSupported = AllSupported(name, programmableSwitchEvent)
}
