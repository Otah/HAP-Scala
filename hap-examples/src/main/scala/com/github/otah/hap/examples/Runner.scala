package com.github.otah.hap.examples

import java.util.concurrent.Executors

import com.github.otah.hap.api._
import com.github.otah.hap.server.beowulfe
import io.github.hapjava.server.HomekitAuthInfo
import io.github.hapjava.server.impl.HomekitServer
import monix.execution.{Ack, Scheduler}
import monix.reactive.subjects.BehaviorSubject

object Runner extends App {

  implicit val scheduler: Scheduler = Scheduler(Executors.newCachedThreadPool())

  val switchStream = BehaviorSubject(false)
  switchStream.subscribe { state =>
    println(s"New state: $state")
    Ack.Continue
  }

  val accessories = Seq(
    1001 --> new ExampleSwitch("An example switch", switchStream),
  )

  // --- the server definition follows ---

  val server = new HomekitServer(1234)

  // TODO here comes some better implementation of storage of authentication information
  val authInfo: HomekitAuthInfo = new InMemoryAuthInfo()

  val bridge = server.createBridge(authInfo, "Example Bridge", "Otah", "BridgeV1", "111bbb222", null, null)

  import beowulfe.BeowulfeAccessoryAdapter.Implicit._ // import implicit accessory converter
  accessories foreach (acc => bridge.addAccessory(acc))

  bridge.start()
}
