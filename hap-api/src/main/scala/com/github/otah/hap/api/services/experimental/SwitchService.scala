package com.github.otah.hap.api.services.experimental

import com.github.otah.hap.api.AccessoryService
import com.github.otah.hap.api.characteristics.PowerStateCharacteristic
import com.github.otah.hap.api.services._

trait SwitchService extends AccessoryService with experimental.OptionalName with Has2Characteristics {

  override final val serviceType = hap.service.switch

  def powerState: PowerStateCharacteristic

  lazy val characteristics = Characteristics(
    id1 ->> name,
    id2 -> powerState,
  )
}
