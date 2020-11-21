package com.github.otah.hap.api.information

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
