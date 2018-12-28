package com.github.otah.hap.api.internal

import scala.concurrent.Future

trait TypedNotifier[T] {

  def subscribe(callback: T => Future[Unit]): Subscription
}
