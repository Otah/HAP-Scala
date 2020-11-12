package com.github.otah.hap.api.services

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._

trait SpeakerService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.speaker

  def mute: Required[MuteCharacteristic]

  def volume: Optional[VolumeCharacteristic]

  lazy val characteristics = Characteristics(name, volume, mute)
}
