package com.github.otah.hap.server

import java.net.InetAddress
import javax.jmdns.{JmDNS, ServiceInfo}

import scala.collection.JavaConverters._

class BonjourAccessoryAdvertiser(address: InetAddress) {

  private val jmdns = JmDNS.create(address)

  def stopAdvertising() = jmdns.unregisterAllServices()

  def startAdvertising(info: BonjourAccessoryAdvertiser.Info) = {

    stopAdvertising() // in case this is a subsequent call

    import info._

    val props = Map[String, String](
      "sf" -> (if (discoverable) "1" else "0"),
      "id" -> mac,
      "md" -> md,
      "c#" -> String.valueOf(`c#`),
      "s#" -> "1",
      "ci" -> String.valueOf(ci),
    )
    jmdns.registerService(ServiceInfo.create("_hap._tcp.local.", label, port, 1, 1, props.asJava))
  }
}

object BonjourAccessoryAdvertiser {

  case class Info(mac: String, port: Int, label: String, md: String, `c#`: Int, ci: Int, discoverable: Boolean)
}
