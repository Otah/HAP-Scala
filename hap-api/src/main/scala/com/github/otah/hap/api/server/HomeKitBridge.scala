package com.github.otah.hap.api.server

import com.github.otah.hap.api._

trait HomeKitBridge {
  bridge =>

  def info: HomeKitInfo

  def accessories: Seq[HomeKitAccessory]

  def selfAndAccessories: Seq[HomeKitAccessory] = asHomeKitAccessory +: accessories

  def asHomeKitAccessory: HomeKitAccessory = new HomeKitAccessory {

    override def id = 1
    override def info = bridge.info
    override def services: Seq[ServiceInstance] = Nil
  }
}
