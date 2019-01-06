package com.github.otah.hap.api.information

case class AccessoryFlags(requiresAdditionalSetup: Boolean)

object AccessoryFlags {
  val NoFlags = AccessoryFlags(requiresAdditionalSetup = false)
}
