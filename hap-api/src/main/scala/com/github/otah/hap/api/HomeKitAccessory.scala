package com.github.otah.hap.api

trait HomeKitAccessory {

  def infoService: Required[AccessoryService]
  def services: Services

  def lowLevelServices: Services = infoService +: services
}
