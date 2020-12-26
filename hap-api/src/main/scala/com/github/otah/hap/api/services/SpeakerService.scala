package com.github.otah.hap.api.services

import com.github.otah.hap.api.characteristics._

trait SpeakerService extends SpecializedService with OptionalName {

  override final val serviceType = hap.service.speaker

  def mute: Required[MuteCharacteristic]

  def volume: Optional[VolumeCharacteristic]

  override def all: AllSupported = AllSupported(name, volume, mute)
}
