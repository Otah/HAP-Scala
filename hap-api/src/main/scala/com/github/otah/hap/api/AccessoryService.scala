package com.github.otah.hap.api

import com.github.otah.hap.api.services.Characteristics

trait AccessoryService extends TypeConvenience {
  parent =>

  def serviceType: HapType
  def characteristics: Characteristics

  def :+(characteristicToAdd: Identified[LowLevelCharacteristic]): AccessoryService = new AccessoryService {

    override def serviceType = parent.serviceType
    override def characteristics = parent.characteristics :+ characteristicToAdd
  }
}

object AccessoryService {

  def apply(service: HapType): AccessoryService = new AccessoryService() {

    override def serviceType = service
    override def characteristics: Characteristics = Nil
  }

  def apply(service: HapTypes.service.type => HapType): AccessoryService = apply(service(HapTypes.service))
}
