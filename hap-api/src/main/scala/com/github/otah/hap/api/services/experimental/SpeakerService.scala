package com.github.otah.hap.api.services.experimental

import com.github.otah.hap.api.AccessoryService
import com.github.otah.hap.api.characteristics._
import com.github.otah.hap.api.services._

trait SpeakerService extends AccessoryService with OptionalName with Has3Characteristics {

  override final val serviceType = hap.service.speaker

  def mute: MuteCharacteristic

  def volume: Option[VolumeCharacteristic]

  lazy val characteristics = Characteristics(
    id1 ->> name,
    id2 ->> volume,
    id3 -> mute,
  )
}
