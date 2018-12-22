package com.github.otah.hap.api.accessories

import com.github.otah.hap.api.HomeKitAccessory

trait IdentifyByPrintingLabel {

  this: HomeKitAccessory =>

  override val identification = () => println("Identification: " + label)
}
