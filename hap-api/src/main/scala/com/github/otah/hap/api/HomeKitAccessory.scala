package com.github.otah.hap.api

trait HomeKitAccessory extends HomeKitInfo {

  def id: Int
  def services: Seq[ServiceInstance]

  def identification: () => Unit
}
