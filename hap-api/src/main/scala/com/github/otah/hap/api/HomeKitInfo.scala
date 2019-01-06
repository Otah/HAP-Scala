package com.github.otah.hap.api

import com.github.otah.hap.api.information.{AccessoryFlags, Revision}

trait HomeKitInfo {

  def identification: () => Unit

  def label: String
  def serialNumber: String
  def model: String
  def manufacturer: String

  def firmwareRevision: Revision
  def hardwareRevision: Option[Revision]

  def accessoryFlags: AccessoryFlags = AccessoryFlags.NoFlags
}
