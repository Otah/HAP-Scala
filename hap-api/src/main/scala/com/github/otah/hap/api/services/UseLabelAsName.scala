package com.github.otah.hap.api.services

import com.github.otah.hap.api.characteristics.NameCharacteristic

trait UseLabelAsName extends OptionalName {

  def label: String

  override def name: Option[NameCharacteristic] = Some(NameCharacteristic(label))
}
