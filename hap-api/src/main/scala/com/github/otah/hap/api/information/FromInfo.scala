package com.github.otah.hap.api.information

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics.NameCharacteristic
import com.github.otah.hap.api.services.IdStrategy

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

object FromInfo {

  abstract class Of(override val homeKitInfo: HomeKitInfo) extends FromInfo

  /** Creates an Identified accessory info service from an info
    * @param homeKitInfo Info object to be translated to the respective info characteristics.
    * @param iid Instance ID of the accessory service. Defaults to 1
    * @return Identified service for accessory information
    */
  def apply(homeKitInfo: HomeKitInfo, iid: Int = 1): Identified[AccessoryInformation] = {
    iid identifying new FromInfo.Of(homeKitInfo) {
      override def baseInstanceId: Int = iid
    }
  }
}
