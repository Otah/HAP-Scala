package com.github.otah.hap.api

import com.github.otah.hap.api.characteristics.NameCharacteristic

trait AccessoryService extends TypeConvenience {
  parent =>

  def serviceType: HapType
  def characteristics: Seq[LowLevelCharacteristic]

  def :+(characteristicToAdd: LowLevelCharacteristic): AccessoryService = new AccessoryService {

    override def serviceType = parent.serviceType
    override def characteristics = parent.characteristics :+ characteristicToAdd
  }

  def named(name: String) = this :+ NameCharacteristic(name)
}

object AccessoryService {

  def apply(service: HapType): AccessoryService = new AccessoryService() {

    override def serviceType = service
    override def characteristics: Seq[LowLevelCharacteristic] = Nil
  }

  def apply(service: HapTypes.service.type => HapType): AccessoryService = apply(service(HapTypes.service))
}
