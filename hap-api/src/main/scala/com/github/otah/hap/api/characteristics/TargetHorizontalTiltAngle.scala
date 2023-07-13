package com.github.otah
package hap.api
package characteristics

trait TargetHorizontalTiltAngle extends TiltAngleCharacteristic {

  override val characteristicType: HapType = HapType.Apple("7B")

  override def writer: Some[Writer]
}
