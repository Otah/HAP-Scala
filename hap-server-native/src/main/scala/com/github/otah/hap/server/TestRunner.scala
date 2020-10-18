package com.github.otah.hap.server

import java.net.InetAddress
import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal

import scala.concurrent.ExecutionContext
import scala.util.Success

object TestRunner extends App {

  val label = "bla" //TODO so far it seems it doesn't work with spaces etc. in Akka HTTP (Host header broken)
  val port = 53001
  val mac = "46:01:72:01:63:f7"
  val `c#` = 1
  val md = "Blah1,1"
  val ci = 1

  val adInfo = BonjourAccessoryAdvertiser.Info(mac, port, label, md, `c#`, ci, discoverable = true)

  val advertiser = new BonjourAccessoryAdvertiser(InetAddress.getByName("10.11.0.156"))
  advertiser.startAdvertising(adInfo)

  implicit val ec = ExecutionContext.fromExecutor(Executors.newCachedThreadPool())
  implicit val as = ActorSystem()

  val handler = pathPrefix("pair-setup") {
    extractRequestEntity { entity =>
      complete {
        Unmarshal(entity).to[Array[Byte]] andThen {
          case Success(value) => println(s"Incoming: ${value.mkString(",")}")
        } map (_ => StatusCodes.OK)
      }
    }
  } ~ extractRequest { request =>
    println(request)
    complete(StatusCodes.OK)
  }

  Http().bindAndHandle(handler, "0.0.0.0", port)

  sys.addShutdownHook {
    advertiser.stopAdvertising()
  }
}
