package com.github.otah.hap.api

trait HomeKitAccessory {

  def label: String
  def serialNumber: String
  def model: String
  def manufacturer: String
  def services: Services

  def identification: () => Unit
}
