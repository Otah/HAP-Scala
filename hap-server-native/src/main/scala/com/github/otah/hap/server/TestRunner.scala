package com.github.otah.hap.server

import java.net.InetAddress
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicReference

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.github.otah.hap.api.server._
import io.github.hapjava.server.impl.HomekitUtils
import io.github.hapjava.server.impl.pairing.PairSetupHandlerProxy

import scala.concurrent.ExecutionContext

object TestRunner extends App {

  val label = "bla" //TODO so far it seems it doesn't work with spaces etc. in Akka HTTP (Host header broken)
  val port = 53001
  val mac = HomekitUtils.generateMac() // TODO make static later
  println(s"Starting with MAC address $mac")
  val `c#` = 1
  val md = "Blah1,1"
  val ci = 1

  val pin = "999-00-666"
  val salt = BigInt("-44368483166755255769016051680614042891")
  val privateKey = Seq[Byte](74,-42,9,100,-25,8,110,43,17,107,-42,116,-35,-1,-86,116,-2,-35,127,100,75,96,112,-2,49,60,-58,-44,-49,21,103,-107)

  val auth = HomeKitAuthentication(
    securityInfo = AuthSecurityInfo(mac, salt, privateKey, pin),
    storage = AuthInfoStorage(), //TODO just in memory for now
  )

  val adInfo = BonjourAccessoryAdvertiser.Info(mac, port, label, md, `c#`, ci, discoverable = true)

  val advertiser = new BonjourAccessoryAdvertiser(InetAddress.getByName("10.11.0.156"))
  advertiser.startAdvertising(adInfo)

  implicit val ec = ExecutionContext.fromExecutor(Executors.newCachedThreadPool())
  implicit val as = ActorSystem()

  val srp = new AtomicReference[Option[PairSetupHandlerProxy]]()

  def printTlvMessage(message: TlvMessage): Unit =
    message.chunks.map(ch => s"${ch.typeByte}: ${ch.value.mkString(",")}").foreach(println)

  val handler = pathPrefix("pair-setup") {
    extractRequestEntity { entity =>
      complete {
        Unmarshal(entity).to[Array[Byte]] map { incoming =>

          val incomingMessage = TlvMessage(incoming)
          println(s"Incoming:")
          printTlvMessage(incomingMessage)

          if (incomingMessage.firstOfType(6).flatMap(_.value.headOption).contains(1))
            srp.set(Some(new PairSetupHandlerProxy(auth)))

          val responseBytes = srp.get().toArray.flatMap(_.responseFor(incomingMessage))
          println(s"Response:")
          printTlvMessage(TlvMessage(responseBytes))

          HttpResponse(entity = HttpEntity(
            ContentType(MediaType.applicationBinary("pairing+tlv8", MediaType.NotCompressible)),
            responseBytes
          ))
        }
      }
    }
  } ~ extractRequest { request =>
    println(request)
    complete(StatusCodes.NotFound)
  }

  Http().bindAndHandle(handler, "0.0.0.0", port)

  sys.addShutdownHook {
    advertiser.stopAdvertising()
  }
}
