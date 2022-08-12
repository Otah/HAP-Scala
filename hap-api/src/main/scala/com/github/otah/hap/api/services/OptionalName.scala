package com.github.otah.hap.api
package services

trait OptionalName {
  this: SpecializedService =>

  def name: Optional[TypedCharacteristic[String]] = None
}
