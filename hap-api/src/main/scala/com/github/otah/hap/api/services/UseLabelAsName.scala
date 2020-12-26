package com.github.otah.hap.api.services

import com.github.otah.hap.api.characteristics.NameCharacteristic

trait UseLabelAsName extends OptionalName {

  this: SpecializedService with IdStrategy.Automatic =>

  def label: String

  override def name = Some(NameCharacteristic(label))
}
