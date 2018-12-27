package com.github.otah.hap.examples

import java.util.concurrent.Executors

import com.beowulfe.hap._
import com.github.otah.hap.api.HomeKitAccessory
import com.github.otah.hap.server.beowulfe
import monix.reactive.subjects.BehaviorSubject

import scala.concurrent.ExecutionContext

object Runner extends App {

  implicit val exec: ExecutionContext = ExecutionContext.fromExecutor(Executors.newCachedThreadPool())

  val switchStream = BehaviorSubject(false)

  val accessories: Seq[HomeKitAccessory] = Seq(
    ExampleSwitch(1001, "An example switch")(switchStream),
  )

  // --- the server definition follows ---

  val server = new HomekitServer(1234)

  val authInfo: HomekitAuthInfo = ??? // TODO here comes the implementation of storage of authentication information

  val bridge = server.createBridge(authInfo, "Example Bridge", "Otah", "BridgeV1", "111bbb222")

  import beowulfe.BeowulfeAccessoryAdapter.Implicit._ // import implicit accessory converter
  accessories foreach (acc => bridge.addAccessory(acc))

  bridge.start()
}
