package com.github.otah.hap.api

trait IdentifyByPrintingLabel {

  this: HomeKitAccessory =>

  override val identification = () => println("Identification: " + label)
}
