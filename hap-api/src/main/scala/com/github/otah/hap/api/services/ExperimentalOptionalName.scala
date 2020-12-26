package com.github.otah.hap.api.services

import com.github.otah.hap.api.Characteristic

trait ExperimentalOptionalName {
  this: HigherKindService =>

  def name: Opt[Characteristic[String]] = None
}
