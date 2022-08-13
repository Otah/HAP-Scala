package com.github.otah.hap.api
package information

import spray.json._

trait RevisionCharacteristic extends TypedCharacteristic[Revision] {

  override def format: String = "string"

  override def toJsonValue(value: Revision): JsValue =
    JsString(value.asString)

  override def fromJsonValue(json: JsValue): Revision =
    throw new IllegalStateException("Revision is never writable")

  override final def isReadable: Boolean = true
  override final def isWritable: Boolean = false
  override final def hasEvents: Boolean = false
}

object RevisionCharacteristic {

  trait Hardware extends RevisionCharacteristic {
    override val characteristicType = hap.characteristic.hardware.revision
  }

  trait Firmware extends RevisionCharacteristic {
    override val characteristicType = hap.characteristic.firmware.revision
  }
}
