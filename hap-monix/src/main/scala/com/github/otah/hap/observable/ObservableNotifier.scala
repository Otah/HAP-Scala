package com.github.otah.hap.observable

import com.github.otah.hap.api.{Subscription, TypedNotifier}
import monix.execution.{Ack, Scheduler}
import monix.reactive.Observable

import scala.concurrent.Future

case class ObservableNotifier[T](observable: Observable[T])(implicit scheduler: Scheduler) extends TypedNotifier[T] {

  override def subscribe(callback: T => Future[Unit]) = new Subscription {

    private val subscription = observable.subscribe(callback.andThen(_ => Ack.Continue))

    override def unsubscribe(): Unit = subscription.cancel()
  }
}
