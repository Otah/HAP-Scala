package com.github.otah.hap.server.beowulfe

import java.net.InetAddress

import com.github.otah.hap.api._
import com.github.otah.hap.api.information._
import com.github.otah.hap.api.server._
import com.typesafe.scalalogging.Logger
import io.github.hapjava.server.impl.HomekitServer

import scala.concurrent.{ExecutionContext, Future}

class BeowulfeDriver()(implicit ec: ExecutionContext) extends HomeKitDriver[Any] {

  private val log = Logger[BeowulfeDriver]

  override def run(server: HomeKitServer)(implicit auth: HomeKitAuthentication): HomeKitDriver.Run[Any] = {
    import server._

    val serverFut = Future {
      val netAddress = host map InetAddress.getByName getOrElse InetAddress.getLocalHost
      log.info(s"HomeKit server address is $netAddress, port $port")
      new HomekitServer(netAddress, port)
    }

    def structuredInfoFromAccessory(accessory: HomeKitAccessory): HomeKitInfo = {
      accessory match {
        case provider: InfoProvider => provider.homeKitInfo
        case other =>
          other.infoService.service match {
            case provider: InfoProvider => provider.homeKitInfo
            case is: AccessoryInformation => HomeKitInfo.fromAccessoryInfo(is)
            //FIXME it would be really great if this works generally
            case _ => throw new UnsupportedOperationException(s"$this requires the bridge and accessories " +
              s"to implement ${classOf[InfoProvider]} or infoService to be an instance of ${classOf[AccessoryInformation]}" +
              s"to be able to extract structured accessory information.")
          }
      }
    }

    val jAuth = new BeowulfeAuthInfoAdapter(auth)

    server.root.rootDevice match {

      case Right(bridge) =>
        val info = bridge match {
          case provider: InfoProvider => provider.homeKitInfo
          case other => structuredInfoFromAccessory(other.asHomeKitAccessory.accessory)
        }
        import info._
        val bridgeFut = serverFut map { jServer =>
          val jBridge = jServer.createBridge(jAuth, label, manufacturer, model, serialNumber, firmwareRevision.asString, hardwareRevision.map(_.asString).orNull)

          bridge.accessories foreach {
            case (aid, accessory) =>
              jBridge.addAccessory(new BeowulfeAccessoryAdapter(aid, accessory, structuredInfoFromAccessory(accessory)))
          }

          jBridge.start()
          jBridge
        }

        new HomeKitDriver.Run[Any] {
          override def initialized(): Future[Any] = bridgeFut
          override def shutdown(): Future[_] = serverFut.map(_.stop()) zip bridgeFut.map(_.stop())
        }

      case Left(standalone) =>
        val init = serverFut map { jServer =>
          val x = jServer.createStandaloneAccessory(
            jAuth,
            new BeowulfeAccessoryAdapter(
              InstanceId(2), // underlying framework puts creates an artificial root anyway, so we cannot use 1
              standalone,
              structuredInfoFromAccessory(standalone)
            )
          )
          x.start()
        }
        log.warn("Using standalone server which doesn't support stopping by the underlying framework design")

        new HomeKitDriver.Run[Any] {
          override def initialized(): Future[Any] = init
          override def shutdown(): Future[_] = serverFut.map(_.stop())
        }
    }
  }
}
