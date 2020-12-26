package com.github.otah.hap.api.services

import com.github.otah.hap.api.characteristics.NameCharacteristic

trait UseLabelAsName extends OptionalName {

  this: HigherKindService with HigherKindService.Automatic =>

  def label: String

  override def name = Some(NameCharacteristic(label))
}
