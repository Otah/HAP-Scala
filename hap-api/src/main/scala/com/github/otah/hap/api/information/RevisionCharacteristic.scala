package com.github.otah.hap.api.information

import com.github.otah.hap.api.HapType
import com.github.otah.hap.api.characteristics.StringCharacteristic

import scala.concurrent.Future

trait RevisionCharacteristic extends StringCharacteristic {

  override def description = "A read-only information" //TODO make None once optional

  def revision: Revision

  override val reader = Reader(Future.successful(revision.asString))

  override final val writer = None
  override final val notifier = None
}

object RevisionCharacteristic {

  import com.github.otah.hap.api.{HapTypes => hap}

  case class Instance(characteristicType: HapType, revision: Revision, maxLength: Int = 64) extends RevisionCharacteristic

  def hardware(revision: Revision): RevisionCharacteristic = Instance(hap.characteristic.hardware.revision, revision)
  def firmware(revision: Revision): RevisionCharacteristic = Instance(hap.characteristic.firmware.revision, revision)
}
