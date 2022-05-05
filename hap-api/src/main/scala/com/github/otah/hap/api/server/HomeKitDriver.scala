package com.github.otah.hap.api.server

import scala.concurrent.Future

trait HomeKitDriver[+T] {

  def run(server: HomeKitServer)(implicit auth: HomeKitAuthentication): HomeKitDriver.Run[T]
}

object HomeKitDriver {

  trait Run[+T] {
    def initialized(): Future[T]
    def shutdown(): Future[_]
  }
}
