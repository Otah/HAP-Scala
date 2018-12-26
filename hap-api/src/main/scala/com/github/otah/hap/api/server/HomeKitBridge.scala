package com.github.otah.hap.api.server

import com.github.otah.hap.api._

trait HomeKitBridge extends HomeKitInfo {
  bridge =>

  def accessories: Seq[HomeKitAccessory]

  def selfAndAccessories: Seq[HomeKitAccessory] = asHomeKitAccessory +: accessories

  def asHomeKitAccessory: HomeKitAccessory = new HomeKitAccessory {

    override def id = 1

    override def identification = () => {}

    override def services: Seq[ServiceInstance] = Nil

    override def label = bridge.label

    override def serialNumber = bridge.serialNumber

    override def model = bridge.model

    override def manufacturer = bridge.manufacturer
  }
}

object HomeKitBridge {

  private case class HKB(label: String, manufacturer: String, model: String, serialNumber: String,
                         accessories: Seq[HomeKitAccessory]) extends HomeKitBridge

  def apply(label: String, manufacturer: String, model: String, serialNumber: String,
            accessories: Seq[HomeKitAccessory]): HomeKitBridge = HKB(label, serialNumber, model, manufacturer, accessories)
}
