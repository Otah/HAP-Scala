package com.github.otah
package hap.api
package characteristics

trait TiltAngleCharacteristic extends IntCharacteristic with Units.ArcDegrees {

  override def min: Int = -90
  override def max: Int = 90

  override def reader: Some[Reader]
  override def notifier: Some[Notifier]
}
