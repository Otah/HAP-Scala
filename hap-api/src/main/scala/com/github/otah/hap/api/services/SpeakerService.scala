package com.github.otah.hap.api.services

import com.github.otah.hap.api.AccessoryService
import com.github.otah.hap.api.characteristics._

trait SpeakerService extends AccessoryService with OptionalName {

  override final val serviceType = hap.service.speaker

  def mute: MuteCharacteristic

  def volume: Option[VolumeCharacteristic]

  lazy val characteristics: Characteristics = Seq(mute) ++ volume ++ name
}
