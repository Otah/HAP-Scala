package com.github.otah.hap.api.information

trait InfoProvider {

  def homeKitInfo: HomeKitInfo
}

object InfoProvider {

  trait Self extends InfoProvider {
    this: HomeKitInfo =>

    override def homeKitInfo: HomeKitInfo = this
  }
}
