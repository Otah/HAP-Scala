package com.github.otah.hap.api.services

import com.github.otah.hap.api.Characteristic

trait OptionalName {
  this: SpecializedService =>

  def name: Optional[Characteristic[String]] = None
}
