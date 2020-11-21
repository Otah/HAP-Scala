package com.github.otah.hap.api.information

trait IdentifyByPrintingLabel {

  this: HomeKitInfo =>

  override val identification = () => println("Identification: " + label)
}
