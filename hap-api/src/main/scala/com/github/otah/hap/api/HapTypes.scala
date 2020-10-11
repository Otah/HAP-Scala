package com.github.otah.hap.api

object HapTypes {

  import HapType._

  object service {

    val accessoryInformation = Apple("3E")

    val fan                = Apple("40")
    val lightbulb          = Apple("43")
    val switch             = Apple("49")
    val programmableSwitch = Apple("89")
    val speaker            = Apple("113")
    val outlet             = Apple("47")
    val battery            = Apple("96")

    object sensor {
      val motion           = Apple("85")
      val contact          = Apple("80")
      val humidity         = Apple("82")
      val temperature      = Apple("8A")
    }
  }

  object characteristic {
    val identify            = Apple("14")
    val manufacturer        = Apple("20")
    val model               = Apple("21")
    val serialNumber        = Apple("30")
    val accessoryProperties = Apple("A6")

    object firmware {
      val revision = Apple("52")
    }
    object hardware {
      val revision = Apple("53")
    }

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
    val battery                 = Apple("68")
    val statusLowBattery        = Apple("79")
    val chargingState           = Apple("8F")

    object temperature {
      val current = Apple("11")
    }
    object relativeHumidity {
      val current = Apple("10")
    }
  }
}
