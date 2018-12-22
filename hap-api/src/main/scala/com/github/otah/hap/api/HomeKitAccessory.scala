package com.github.otah.hap.api

trait HomeKitAccessory {

  def id: Int
  def label: String
  def serialNumber: String
  def model: String
  def manufacturer: String
  def services: Seq[AccessoryService]

  def identification: () => Unit
}
