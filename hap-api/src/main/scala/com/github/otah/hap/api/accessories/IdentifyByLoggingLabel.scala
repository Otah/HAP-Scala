package com.github.otah.hap.api.accessories

import com.github.otah.hap.api.HomeKitInfo
import com.typesafe.scalalogging.Logger

trait IdentifyByLoggingLabel {

  this: HomeKitInfo =>

  private val log = Logger(getClass)

  override val identification = () => log.info("Identification: " + label)
}
