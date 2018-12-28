package com.github.otah.hap.api.internal

trait ReadWriteNotify[T] {

  this: Characteristic[T] =>

  override def reader: Some[Reader]
  override def writer: Some[Writer]
  override def notifier: Some[Notifier]
}
