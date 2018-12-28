package com.github.otah.hap.api

import com.github.otah.hap.api.internal.Characteristic

trait OptionalName {

  def name: Option[Characteristic[String]] = None
}
