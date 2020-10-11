package com.github.otah.hap.api.information

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._
import com.github.otah.hap.api.services._

trait AccessoryInformation extends AccessoryService with Has8Characteristics {

  override final val serviceType = hap.service.accessoryInformation

  def identify: IdentifyCharacteristic
  def manufacturer: InfoCharacteristic
  def model: InfoCharacteristic
  def name: NameCharacteristic
  def serialNumber: InfoCharacteristic
  def firmwareRevision: RevisionCharacteristic

  def hardwareRevision: Option[RevisionCharacteristic]
  def accessoryFlags: Option[LowLevelCharacteristic] //TODO proper type

  override def characteristics = Characteristics(
    id1 -> name,
    id2 -> identify,
    id3 -> manufacturer,
    id4 -> model,
    id5 -> serialNumber,
    id6 -> firmwareRevision,
    id7 -> hardwareRevision,
    id8 -> accessoryFlags,
  )
}
