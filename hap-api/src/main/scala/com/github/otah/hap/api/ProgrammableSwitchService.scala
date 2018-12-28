package com.github.otah.hap.api

trait ProgrammableSwitchService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.programmableSwitch

  def programmableSwitchEvent: ProgrammableSwitchEvent

  lazy val characteristics: Characteristics = Seq(programmableSwitchEvent) ++ name //TODO namespace & label index
}
