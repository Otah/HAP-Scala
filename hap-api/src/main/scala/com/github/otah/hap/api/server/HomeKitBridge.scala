package com.github.otah.hap.api.server

import com.github.otah.hap.api._
import com.github.otah.hap.api.information._

trait HomeKitBridge {
  bridge =>

  def infoService: Required[AccessoryService]

  def accessories: Seq[Identified[HomeKitAccessory]]

  def selfAndAccessories: Seq[Identified[HomeKitAccessory]] = asHomeKitAccessory +: accessories

  def asHomeKitAccessory: Identified[HomeKitAccessory] = 1 identifying new HomeKitAccessory {
    override def infoService: Required[AccessoryService] = bridge.infoService
    override def services: Services = Nil
  }
}

object HomeKitBridge {

  trait WithInfo extends HomeKitBridge {

    def info: HomeKitInfo

    override def infoService: Required[AccessoryService] = FromInfo(info)

    def asRoot(implicit auth: HomeKitAuthentication) = HomeKitRoot.bridge(this, info.label)
  }

  object WithInfo {
    private class Impl(val info: HomeKitInfo, val accessories: Seq[Identified[HomeKitAccessory]]) extends WithInfo
    def apply(info: HomeKitInfo)(accessories: Identified[HomeKitAccessory]*): WithInfo = new Impl(info, accessories)
  }
}
