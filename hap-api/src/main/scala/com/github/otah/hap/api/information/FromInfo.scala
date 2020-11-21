package com.github.otah.hap.api.information

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics.NameCharacteristic
import com.github.otah.hap.api.services.experimental._

trait FromInfo extends AccessoryInformationWithAutoIds with InfoProvider {

  override def identify = IdentifyCharacteristic(info.identification)

  override def manufacturer = InfoCharacteristic.manufacturer(info.manufacturer)

  override def model = InfoCharacteristic.model(info.model)

  override def name = NameCharacteristic(info.label) //TODO is label the correct info?

  override def serialNumber = InfoCharacteristic.serialNumber(info.serialNumber)

  override def firmwareRevision = RevisionCharacteristic.firmware(info.firmwareRevision)

  override def hardwareRevision = info.hardwareRevision map RevisionCharacteristic.hardware

  override def accessoryFlags = AccessoryFlagsCharacteristic.fixed(info.accessoryFlags)
}

object FromInfo {

  abstract class Of(override val info: HomeKitInfo) extends FromInfo

  /** Creates an Identified accessory info service from an info
    * @param info Info object to be translated to the respective info characteristics.
    * @param iid Instance ID of the accessory service. Defaults to 1
    * @return Identified service for accessory information
    */
  def apply(info: HomeKitInfo, iid: Int = 1): Identified[AccessoryInformationWithAutoIds] = {
    iid <=> new FromInfo.Of(info) with SequentialInstanceIds {
      override def baseInstanceId: Int = iid
    }
  }
}
