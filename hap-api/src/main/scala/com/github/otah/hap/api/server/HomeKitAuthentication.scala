package com.github.otah.hap.api.server

import com.github.otah.hap.api.HomeKitAccessory

trait HomeKitAuthentication {

  def storage: AuthInfoStorage
  
  def securityInfo: AuthSecurityInfo

  def bridge(bridge: HomeKitBridge) = HomeKitRoot.bridge(bridge, this)
  def standaloneAccessory(accessory: HomeKitAccessory) = HomeKitRoot.standaloneAccessory(accessory, this)
}

object HomeKitAuthentication {

  private case class HKA(storage: AuthInfoStorage, securityInfo: AuthSecurityInfo) extends HomeKitAuthentication

  def apply(storage: AuthInfoStorage, securityInfo: AuthSecurityInfo): HomeKitAuthentication = HKA(storage, securityInfo)
}
