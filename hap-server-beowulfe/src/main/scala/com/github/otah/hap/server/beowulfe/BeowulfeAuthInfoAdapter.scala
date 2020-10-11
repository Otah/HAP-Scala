package com.github.otah.hap.server.beowulfe

import java.math.BigInteger

import com.github.otah.hap.api.server.HomeKitAuthentication
import io.github.hapjava.server.HomekitAuthInfo

class BeowulfeAuthInfoAdapter(authentication: HomeKitAuthentication) extends HomekitAuthInfo {

  import authentication._

  override def getUserPublicKey(username: String): Array[Byte] = storage.getAsArray(username).orNull

  override def createUser(username: String, publicKey: Array[Byte]): Unit = storage.add(username, publicKey)

  override def removeUser(username: String): Unit = storage.remove(username)

  override def getMac: String = securityInfo.mac

  override def getPin: String = securityInfo.pin

  override def getSalt: BigInteger = securityInfo.salt.bigInteger

  override def getPrivateKey: Array[Byte] = securityInfo.privateKey.toArray
}
