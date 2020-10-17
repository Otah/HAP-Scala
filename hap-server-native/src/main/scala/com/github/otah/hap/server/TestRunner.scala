package com.github.otah.hap.server

import java.net.InetAddress
import java.util.concurrent.Executors
import javax.jmdns.{JmDNS, ServiceInfo}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext
import scala.util.Success

object TestRunner extends App {

  val label = "bla" //TODO so far it seems it doesn't work with spaces etc.
  val port = 53001
  val mac = "46:01:72:01:63:f7"
  val `c#` = 1
  val md = "Blah1,1"
  val ci = 1


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


  val jmdns = JmDNS.create(InetAddress.getByName("10.11.0.156"))

  val props = Map[String, String](
    "sf" -> "1", // discoverable ? "1" : "0",
    "id" -> mac,
    "md" -> md,
    "c#" -> String.valueOf(`c#`),
    "s#" -> "1",
//    "ff" -> "0",
    "ci" -> String.valueOf(ci),
  )
  jmdns.registerService(ServiceInfo.create("_hap._tcp.local.", label, port, 1, 1, props.asJava))

  sys.addShutdownHook {
    jmdns.unregisterAllServices()
  }
}
