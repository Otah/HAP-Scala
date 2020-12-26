package com.github.otah.hap.api

trait HomeKitAccessory {

  def infoService: Identified[AccessoryService]
  def services: Services

  def lowLevelServices: Services = infoService +: services
}
