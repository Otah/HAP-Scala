package com.github.otah.hap.api

trait HomeKitAccessory {

  def infoService: Service
  def services: Services

  def lowLevelServices: Services = infoService +: services
}
