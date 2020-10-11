package com.github.otah.hap.api

trait IdentifyByPrintingLabel {

  this: HomeKitInfo =>

  override val identification = () => println("Identification: " + label)
}
