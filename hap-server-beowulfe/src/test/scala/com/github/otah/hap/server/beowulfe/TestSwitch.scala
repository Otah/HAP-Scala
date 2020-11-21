package com.github.otah.hap.server.beowulfe

import com.github.otah.hap.api._
import com.github.otah.hap.api.characteristics.PowerStateCharacteristic
import com.github.otah.hap.api.information._
import com.github.otah.hap.api.services._
import com.github.otah.hap.api.services.experimental._
import com.github.otah.hap.api.{AccessoryService, HomeKitAccessory}

import scala.concurrent.{ExecutionContext, Future}

case class TestSwitch(label: String)(implicit ec: ExecutionContext)
        extends HomeKitAccessory with HomeKitInfo with SingleServiceAccessory with AccessoryService with SequentialInstanceIds
                with IdentifyByPrintingLabel with experimental.SwitchService with PowerStateCharacteristic {

  @volatile private var state = false

  override def manufacturer = "Otah"

  override def model = "none"

  override def serialNumber = "none"

  override def description = Some("On / Off state")

  override def firmwareRevision = Revision("0.0.1")

  override def hardwareRevision = None

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

  override def powerState = this

  override def infoService = FromInfo(this)
}
