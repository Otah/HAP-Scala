package com.github.otah.hap.api

import com.typesafe.scalalogging.Logger

trait IdentifyByLoggingLabel {

  this: HomeKitAccessory =>

  private val log = Logger(getClass)

  override val identification = () => log.info("Identification: " + label)
}
