package com.github.otah.hap.api

trait HomeKitAccessory {

  def info: HomeKitInfo
  def services: Services
}
