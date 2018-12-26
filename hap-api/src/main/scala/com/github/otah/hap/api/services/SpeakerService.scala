package com.github.otah.hap.api.services

import com.github.otah.hap.api.AccessoryService
import com.github.otah.hap.api.characteristics._

trait SpeakerService extends AccessoryService with OptionalName with Has3Characteristics {

  override final val serviceType = hap.service.speaker

  def mute: MuteCharacteristic

  def volume: Option[VolumeCharacteristic]

  lazy val characteristics: Characteristics = Seq(
    id1 - name,
    id2 - volume,
    id3 - mute,
  )
}
