package com.github.otah.hap.api.information

import com.github.otah.hap.api.HomeKitInfo
import com.github.otah.hap.api.characteristics.NameCharacteristic

abstract class FromInfo(info: HomeKitInfo) extends AccessoryInformationWithAutoIds {

  override def identify = IdentifyCharacteristic(info.identification)

  override def manufacturer = InfoCharacteristic.manufacturer(info.manufacturer)

  override def model = InfoCharacteristic.model(info.model)

  override def name = NameCharacteristic(info.label) //TODO is label the correct info?

  override def serialNumber = InfoCharacteristic.serialNumber(info.serialNumber)

  override def firmwareRevision = RevisionCharacteristic.firmware(info.firmwareRevision)

  override def hardwareRevision = info.hardwareRevision map RevisionCharacteristic.hardware

  override def accessoryFlags = AccessoryFlagsCharacteristic.fixed(info.accessoryFlags)
}
