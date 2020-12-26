package com.github.otah.hap.api.services

import com.github.otah.hap.api.Characteristic

trait OptionalName {
  this: HigherKindService =>

  def name: Optional[Characteristic[String]] = None
}
