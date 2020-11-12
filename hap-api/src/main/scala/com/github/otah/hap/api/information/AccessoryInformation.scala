package com.github.otah.hap.api.information

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._
import com.github.otah.hap.api.services._

trait AccessoryInformation extends AccessoryService {

  override final val serviceType = hap.service.accessoryInformation

  def identify: Required[IdentifyCharacteristic]
  def manufacturer: Required[InfoCharacteristic]
  def model: Required[InfoCharacteristic]
  def name: Required[NameCharacteristic]
  def serialNumber: Required[InfoCharacteristic]
  def firmwareRevision: Required[RevisionCharacteristic]

  def hardwareRevision: Optional[RevisionCharacteristic]
  def accessoryFlags: Optional[LowLevelCharacteristic] //TODO proper type

  override def characteristics = Characteristics(
    name,
    identify,
    manufacturer,
    model,
    serialNumber,
    firmwareRevision,
    hardwareRevision,
    accessoryFlags,
  )
}
