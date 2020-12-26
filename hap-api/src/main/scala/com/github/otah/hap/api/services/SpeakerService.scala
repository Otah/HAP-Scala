package com.github.otah.hap.api.services

import com.github.otah.hap.api.characteristics._

trait SpeakerService extends HigherKindService with OptionalName {

  override final val serviceType = hap.service.speaker

  def mute: Required[MuteCharacteristic]

  def volume: Optional[VolumeCharacteristic]

  override def options: Options = Options(name, volume, mute)
}
