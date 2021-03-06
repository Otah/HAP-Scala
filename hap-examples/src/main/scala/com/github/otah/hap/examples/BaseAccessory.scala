package com.github.otah.hap.examples

import com.github.otah.hap.api._
import com.github.otah.hap.api.accessories._

/** It is usually handy to define a common accessory which will cover the most typical scenarios of your setup.
  */
trait BaseAccessory extends SingleServiceAccessory with IdentifyByPrintingLabel {

  this: AccessoryService =>

  override def manufacturer = "Otah"
  override def model = "Virtual Accessory"
  override def serialNumber = "none"
}
