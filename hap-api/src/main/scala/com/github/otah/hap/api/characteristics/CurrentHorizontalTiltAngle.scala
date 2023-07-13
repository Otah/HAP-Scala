package com.github.otah
package hap.api
package characteristics

trait CurrentHorizontalTiltAngle extends TiltAngleCharacteristic {

  override val characteristicType: HapType = HapType.Apple("6C")

  override def writer: None.type = None
}
