package com.github.otah.hap.api.services

import com.github.otah.hap.api._

trait OptionalName2 {

  def name: Option[Identified[Characteristic[String]]] = None
}
