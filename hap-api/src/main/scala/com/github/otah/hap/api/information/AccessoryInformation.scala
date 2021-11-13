package com.github.otah.hap.api.information

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics._
import com.github.otah.hap.api.services._

trait AccessoryInformation extends SpecializedService {

  override final val serviceType = hap.service.accessoryInformation

  def identify: Required[IdentifyCharacteristic]
  def manufacturer: Required[InfoCharacteristic]
  def model: Required[InfoCharacteristic]
  def name: Required[NameCharacteristic]
  def serialNumber: Required[InfoCharacteristic]
  def firmwareRevision: Required[RevisionCharacteristic]

  def hardwareRevision: Optional[RevisionCharacteristic]
  def accessoryFlags: Optional[LowLevelCharacteristic] //TODO proper type

  override def all: AllSupported = AllSupported(
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

object AccessoryInformation {

  trait FromInfo extends AccessoryInformation with InfoProvider with IdStrategy.Automatic {

    override def identify = IdentifyCharacteristic(homeKitInfo.identification)

    override def manufacturer = InfoCharacteristic.manufacturer(homeKitInfo.manufacturer)

    override def model = InfoCharacteristic.model(homeKitInfo.model)

    override def name = NameCharacteristic(homeKitInfo.label) //TODO is label the correct info?

    override def serialNumber = InfoCharacteristic.serialNumber(homeKitInfo.serialNumber)

    override def firmwareRevision = RevisionCharacteristic.firmware(homeKitInfo.firmwareRevision)

    override def hardwareRevision = homeKitInfo.hardwareRevision map RevisionCharacteristic.hardware

    override def accessoryFlags = AccessoryFlagsCharacteristic.fixed(homeKitInfo.accessoryFlags) map (() => _)
  }

  /** Creates an Identified accessory info service from an info
    * @param info Info object to be translated to the respective info characteristics.
    * @param iid Instance ID of the accessory service. Defaults to 1
    * @return Identified service for accessory information
    */
  def fromInfo(info: HomeKitInfo, iid: Int = 1): Identified[AccessoryInformation] = {
    iid identifying new FromInfo {
      override def homeKitInfo: HomeKitInfo = info
      override def baseInstanceId: Int = iid
    }
  }
}
