package com.github.otah.hap.api.accessories

import com.github.otah.hap.api.HomeKitInfo

trait IdentifyByPrintingLabel {

  this: HomeKitInfo =>

  override val identification = () => println("Identification: " + label)
}
