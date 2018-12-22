package com.github.otah.hap.api.services

import com.github.otah.hap.api.characteristics.ProgrammableSwitchEvent
import com.github.otah.hap.api.{AccessoryService, LowLevelCharacteristic}

trait ProgrammableSwitchService extends AccessoryService with OptionalName {

  override final val serviceId = hap.service.programmableSwitch

  def programmableSwitchEvent: ProgrammableSwitchEvent

  lazy val characteristics: Seq[LowLevelCharacteristic] = Seq(programmableSwitchEvent) ++ name //TODO namespace & label index
}
