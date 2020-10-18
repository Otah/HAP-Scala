package com.github.otah.hap.server

import java.net.InetAddress
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicReference

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal
import io.github.hapjava.server.impl.pairing.SrpHandlerProxy

import scala.concurrent.ExecutionContext
import scala.util.Success

object TestRunner extends App {

  val label = "bla" //TODO so far it seems it doesn't work with spaces etc. in Akka HTTP (Host header broken)
  val port = 53001
  val mac = "46:01:72:01:63:f7"
  val `c#` = 1
  val md = "Blah1,1"
  val ci = 1

  val pin = "999-00-666"
  val salt = BigInt("-44368483166755255769016051680614042891")

  val adInfo = BonjourAccessoryAdvertiser.Info(mac, port, label, md, `c#`, ci, discoverable = true)

  val advertiser = new BonjourAccessoryAdvertiser(InetAddress.getByName("10.11.0.156"))
  advertiser.startAdvertising(adInfo)

  implicit val ec = ExecutionContext.fromExecutor(Executors.newCachedThreadPool())
  implicit val as = ActorSystem()

  val srp = new AtomicReference[Option[SrpHandlerProxy]]()

  val handler = pathPrefix("pair-setup") {
    extractRequestEntity { entity =>
      complete {
        Unmarshal(entity).to[Array[Byte]] andThen {
          case Success(value) => println(s"Incoming: ${value.mkString(",")}")
        } map { incoming =>
          if (TlvMessage(incoming).firstOfType(6).flatMap(_.value.headOption).contains(1)) srp.set(Some(SrpHandlerProxy(pin, salt)))

          val responseBytes = srp.get().toArray.flatMap(_.responseFor(incoming))
          println(s"Response: ${responseBytes.mkString(",")}")

          HttpResponse(entity = HttpEntity(
            ContentType(MediaType.applicationBinary("pairing+tlv8", MediaType.NotCompressible)),
            responseBytes
          ))
        }
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
