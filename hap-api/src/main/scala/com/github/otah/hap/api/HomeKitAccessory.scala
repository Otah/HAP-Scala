package com.github.otah.hap.api

trait HomeKitAccessory {

  def id: Int
  def info: HomeKitInfo
  def services: Seq[ServiceInstance]
}
