package com.github.otah.hap.api

object HapIds {

  import HapId._

  object service {
    val fan                = Apple("40")
    val lightbulb          = Apple("43")
    val switch             = Apple("49")
    val programmableSwitch = Apple("89")
    val speaker            = Apple("113")
    val outlet             = Apple("47")

    object sensor {
      val motion           = Apple("85")
      val contact          = Apple("80")
    }
  }

  object characteristic {
    val brightness              = Apple("8")
    val name                    = Apple("23")
    val powerState              = Apple("25")
    val rotationSpeed           = Apple("29")
    val volume                  = Apple("119")
    val mute                    = Apple("11A")
    val colorTemperature        = Apple("CE")
    val motionDetected          = Apple("22")
    val contactState            = Apple("6A")
    val programmableSwitchEvent = Apple("73")
    val statusActive            = Apple("75")
    val outletInUse             = Apple("26")
  }
}
