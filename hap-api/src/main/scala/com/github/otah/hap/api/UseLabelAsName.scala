package com.github.otah.hap.api

trait UseLabelAsName extends OptionalName {

  def label: String

  override def name: Option[NameCharacteristic] = Some(NameCharacteristic(label))
}
