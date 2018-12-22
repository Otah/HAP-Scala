package com.github.otah.hap.api.services

import com.github.otah.hap.api.Characteristic

trait OptionalName {

  def name: Option[Characteristic[String]] = None
}
