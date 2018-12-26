package com.github.otah.hap.server.beowulfe

import com.github.otah.hap.api.accessories.{IdentifyByPrintingLabel, SingleServiceAccessory}
import com.github.otah.hap.api.characteristics.{NameCharacteristic, PowerStateCharacteristic}
import com.github.otah.hap.api.services._
import com.github.otah.hap.api.{AccessoryService, HomeKitAccessory}

import scala.concurrent.{ExecutionContext, Future}

case class TestSwitch(id: Int, label: String)(implicit ec: ExecutionContext)
        extends HomeKitAccessory with SingleServiceAccessory with AccessoryService with SequenceInstanceIds
                with IdentifyByPrintingLabel with SwitchService with UseLabelAsName with PowerStateCharacteristic {

  @volatile private var state = false

  override def manufacturer = "Otah"

  override def model = "none"

  override def serialNumber = "none"

  override def reader = Reader(Future.successful(state))

  override def writer = Writer { newValue =>
    state = newValue
    Future.successful(state)
  }

  override def notifier = Some {
    new Notifier {
      override def subscribe(callback: Boolean => Future[Unit]) = () => ()
    }
  }

  override def baseInstanceId: Int = 100

  override def name = super.name map (sc => NameCharacteristic(sc.name, 255))

  override def powerState = this
}
