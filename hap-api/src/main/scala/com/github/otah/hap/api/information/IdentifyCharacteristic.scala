package com.github.otah.hap.api.information

import com.github.otah.hap.api.characteristics.BoolCharacteristic

import scala.concurrent.Future

trait IdentifyCharacteristic extends BoolCharacteristic {

  override final val characteristicType = hap.characteristic.identify

  override def description = "Identification routine"

  override final val reader = None

  override final val notifier = None

  override def writer: Some[Writer]
}

object IdentifyCharacteristic {

  def apply(onIdentify: () => Unit): IdentifyCharacteristic = new IdentifyCharacteristic {
    override final val writer = Writer(_ => Future.successful(onIdentify()))
  }
}
