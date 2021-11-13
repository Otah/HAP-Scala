package com.github.otah.hap.api.information

import scala.concurrent.ExecutionContext

trait HomeKitInfo extends InfoProvider.Self {

  def identification: () => Unit

  def label: String
  def serialNumber: String
  def model: String
  def manufacturer: String

  def firmwareRevision: Revision
  def hardwareRevision: Option[Revision]

  def accessoryFlags: AccessoryFlags = AccessoryFlags.NoFlags
}

object HomeKitInfo {

  def fromAccessoryInfo(is: AccessoryInformation)(implicit ec: ExecutionContext) = new FromAccessoryInfo(is)

  class FromAccessoryInfo(is: AccessoryInformation)(implicit ec: ExecutionContext) extends HomeKitInfo {

    override def identification: () => Unit =
      () => is.getCharacteristic(is.identify).writer.value(ec)(true)

    override def label: String =
      is.getCharacteristic(is.name).name

    override def serialNumber: String =
      is.getCharacteristic(is.serialNumber).information

    override def model: String =
      is.getCharacteristic(is.model).information

    override def manufacturer: String =
      is.getCharacteristic(is.manufacturer).information

    override def firmwareRevision: Revision =
      is.getCharacteristic(is.firmwareRevision).revision

    override def hardwareRevision: Option[Revision] =
      is.getCharacteristic(is.hardwareRevision) map (_.revision)
  }
}
