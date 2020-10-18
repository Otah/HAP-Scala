package com.github.otah.hap.server

import java.net.InetAddress
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicReference

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.ToResponseMarshallable
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

  val contentTypePairingTlv = ContentType(MediaType.applicationBinary("pairing+tlv8", MediaType.NotCompressible))

  def extractTlv(f: TlvMessage => ToResponseMarshallable) = extractRequestEntity { entity =>
    complete(Unmarshal(entity).to[Array[Byte]] map TlvMessage.apply map f)
  }

  def tlvResponse(responseMessage: TlvMessage) =
    HttpResponse(entity = HttpEntity(contentTypePairingTlv, responseMessage.asBytes.toArray))

  val pairVerifyHandler = new PairVerifyHandler(auth, label)

  val keys = new AtomicReference[Option[SessionKeys]](None)

  val handler = pathPrefix("pair-setup") {
    extractTlv { incoming =>
      println(s"Incoming:")
      printTlvMessage(incoming)

      if (incoming.firstOfType(6).flatMap(_.value.headOption).contains(1))
        srp.set(Some(new PairSetupHandlerProxy(auth)))

      val responseMessage = srp.get().map(_.respondTo(incoming)).getOrElse(TlvMessage(Nil))
      println(s"Response:")
      printTlvMessage(responseMessage)

      tlvResponse(responseMessage)
    }
  } ~ pathPrefix("pair-verify") {
    extractTlv { incoming =>
      println(s"Incoming:")
      printTlvMessage(incoming)

      val (responseMessage, maybeKeys) = pairVerifyHandler.respondTo(incoming)
      keys.set(maybeKeys)
      println(s"Response:")
      printTlvMessage(responseMessage)

      tlvResponse(responseMessage)
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
