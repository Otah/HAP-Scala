package com.github.otah.hap.api.server

trait HomeKitAuthentication {

  def storage: AuthInfoStorage
  
  def securityInfo: AuthSecurityInfo
}

object HomeKitAuthentication {

  private case class HKA(storage: AuthInfoStorage, securityInfo: AuthSecurityInfo) extends HomeKitAuthentication

  def apply(storage: AuthInfoStorage, securityInfo: AuthSecurityInfo): HomeKitAuthentication = HKA(storage, securityInfo)
}
