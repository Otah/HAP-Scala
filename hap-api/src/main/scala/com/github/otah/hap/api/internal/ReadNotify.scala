package com.github.otah.hap.api.internal

trait ReadNotify[T] {

  this: Characteristic[T] =>

  override def reader: Some[Reader]
  override final val writer = None
  override def notifier: Some[Notifier]
}
