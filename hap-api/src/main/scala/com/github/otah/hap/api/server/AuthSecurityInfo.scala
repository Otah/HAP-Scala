package com.github.otah.hap.api.server

trait AuthSecurityInfo {

  def mac: String
  def salt: BigInt
  def privateKey: Seq[Byte]
  def pin: String
  def setupId: String
}

object AuthSecurityInfo {

  private case class ASI(mac: String, salt: BigInt, privateKey: Seq[Byte], pin: String, setupId: String) extends AuthSecurityInfo

  def apply(mac: String, salt: BigInt, privateKey: Seq[Byte], pin: String, setupId: String): AuthSecurityInfo =
    ASI(mac, salt, privateKey, pin, setupId)
}
