package com.github.otah.hap.api.services.experimental

import com.github.otah.hap.api.Characteristic

trait OptionalName {

  def name: Option[Characteristic[String]] = None
}
