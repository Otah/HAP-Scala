package com.github.otah.hap.server.beowulfe

import java.net.InetAddress

import com.github.otah.hap.api.server.HomeKitServer
import com.typesafe.scalalogging.Logger
import io.github.hapjava.server.impl.HomekitServer

import scala.concurrent.ExecutionContext

object BeowulfeServerDriver {

  private val log = Logger[BeowulfeServerDriver.type]

  def run(serverDef: HomeKitServer)(implicit ec: ExecutionContext): Unit = {

    val netAddress = serverDef.host map InetAddress.getByName getOrElse InetAddress.getLocalHost
    val port = serverDef.port

    log.info(s"HomeKit server address is $netAddress, port $port")

    val server = new HomekitServer(netAddress, port)

    serverDef.bridgesOrAccessories foreach { boa =>
      import BeowulfeAccessoryAdapter.Implicit._

      val authInfo = new BeowulfeAuthInfoAdapter(boa.auth)

      boa.root match {
        case Left(accessory) =>
          server.createStandaloneAccessory(authInfo, accessory)

        case Right(bridgeDef) =>
          val info = bridgeDef.info
          import info._
          val bridge = server.createBridge(
            authInfo, label, manufacturer, model, serialNumber,
            firmwareRevision.asString, hardwareRevision.map(_.asString).orNull
          )
          bridgeDef.accessories foreach (acc => bridge.addAccessory(acc))
      }
    }
  }
}
