package com.github.otah.hap.api.information

trait InfoProvider {

  def info: HomeKitInfo
}

object InfoProvider {

  trait Self extends InfoProvider {
    this: HomeKitInfo =>

    override def info: HomeKitInfo = this
  }
}
