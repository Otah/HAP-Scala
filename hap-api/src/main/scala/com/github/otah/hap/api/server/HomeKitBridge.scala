package com.github.otah.hap.api.server

import com.github.otah.hap.api._

trait HomeKitBridge {
  bridge =>

  def info: HomeKitInfo

  def accessories: Seq[Identified[HomeKitAccessory]]

  def selfAndAccessories: Seq[Identified[HomeKitAccessory]] = asHomeKitAccessory +: accessories

  def asHomeKitAccessory: Identified[HomeKitAccessory] = 1 <=> new HomeKitAccessory {
    override def info = bridge.info
    override def services: Services = Nil
  }
}
