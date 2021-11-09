package com.github.otah.hap.server.beowulfe

import java.math.BigInteger

import com.github.otah.hap.api.server.HomeKitAuthentication
import io.github.hapjava.server.HomekitAuthInfo

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class BeowulfeAuthInfoAdapter(authentication: HomeKitAuthentication) extends HomekitAuthInfo {

  import authentication._

  private def await[T](future: Future[T]): T = Await.result(future, Duration.Inf)

  override def getUserPublicKey(username: String): Array[Byte] = await(storage.getPublicKey(username)).map(_.toArray).orNull

  override def createUser(username: String, publicKey: Array[Byte]): Unit = await(storage.add(username, publicKey.toSeq))

  override def removeUser(username: String): Unit = await(storage.remove(username))

  override def hasUser = !await(storage.isEmpty())

  override def getMac: String = securityInfo.mac

  override def getPin: String = securityInfo.pin

  override def getSetupId: String = securityInfo.setupId

  override def getSalt: BigInteger = securityInfo.salt.bigInteger

  override def getPrivateKey: Array[Byte] = securityInfo.privateKey.toArray
}
