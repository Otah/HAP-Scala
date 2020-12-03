package com.github.otah.hap.examples

import java.util.concurrent.Executors

import com.github.otah.hap.api._
import com.github.otah.hap.server.beowulfe
import io.github.hapjava.server.HomekitAuthInfo
import io.github.hapjava.server.impl.HomekitServer
import monix.execution.Scheduler
import monix.reactive.subjects.BehaviorSubject

object Runner extends App {

  implicit val scheduler = Scheduler(Executors.newCachedThreadPool())

  val switchStream = BehaviorSubject(false)

  val accessories = Seq(
    1001 --> new ExampleSwitch("An example switch", switchStream),
  )

  // --- the server definition follows ---

  val server = new HomekitServer(1234)

  val authInfo: HomekitAuthInfo = ??? // TODO here comes the implementation of storage of authentication information

  val bridge = server.createBridge(authInfo, "Example Bridge", "Otah", "BridgeV1", "111bbb222", null, null)

  import beowulfe.BeowulfeAccessoryAdapter.Implicit._ // import implicit accessory converter
  accessories foreach (acc => bridge.addAccessory(acc))

  bridge.start()
}
