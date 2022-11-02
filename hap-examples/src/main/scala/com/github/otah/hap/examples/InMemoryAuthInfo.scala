package com.github.otah.hap.examples

import java.math.BigInteger
import java.util.concurrent.ConcurrentHashMap
/*

import io.github.hapjava.server.HomekitAuthInfo

class InMemoryAuthInfo extends HomekitAuthInfo {

  override def getSetupId: String = "HAHA"
  override def getPin: String = "111-22-333"
  override def getMac: String = "c2:a0:63:ec:34:22"
  override def getSalt: BigInteger = new BigInteger("-27141133202296566969031800291203693083")
  override def getPrivateKey: Array[Byte] = Array[Byte](
    -119, -73, -118, -32, -93, 45, 26, -60, -8, -75, -71, 60, -120, 32, 102, -78,
    -122, -125, -105, -60, -58, 37, -26, 39, -116, 116, 46, -37, -58, -15, 81, -114
  )

  private val map = new ConcurrentHashMap[String, Array[Byte]]()

  override def hasUser: Boolean = !map.isEmpty

  override def createUser(username: String, publicKey: Array[Byte]): Unit = map.put(username, publicKey)

  override def removeUser(username: String): Unit = map.remove(username)

  override def getUserPublicKey(username: String): Array[Byte] = map.get(username)
}
*/
