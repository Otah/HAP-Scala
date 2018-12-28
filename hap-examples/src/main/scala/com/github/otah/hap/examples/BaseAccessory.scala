package com.github.otah.hap.examples

import java.util.concurrent.Executors

import com.github.otah.hap.api._
import com.github.otah.hap.api.accessories._
import monix.execution.Scheduler

/** It is usually handy to define a common accessory which will cover the most typical scenarios of your setup.
  */
trait BaseAccessory extends SingleServiceAccessory with IdentifyByPrintingLabel {

  this: AccessoryService =>

  protected implicit val scheduler = BaseAccessory.sharedScheduler

  override def manufacturer = "Otah"
  override def model = "Virtual Accessory"
  override def serialNumber = "none"
}

object BaseAccessory {
  val sharedScheduler = Scheduler(Executors.newCachedThreadPool())
}
