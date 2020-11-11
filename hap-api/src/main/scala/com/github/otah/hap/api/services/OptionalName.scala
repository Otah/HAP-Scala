package com.github.otah.hap.api.services

import com.github.otah.hap.api._

trait OptionalName {

  def name: Optional[Characteristic[String]] = None
}
