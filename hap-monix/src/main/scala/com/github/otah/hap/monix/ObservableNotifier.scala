package com.github.otah
package hap.monix

import hap.api._
import monix.execution.{Ack, Scheduler}
import monix.reactive.Observable
import monix.reactive.subjects.ConcurrentSubject

import scala.concurrent.Future

class ObservableNotifier(observable: Observable[Update])(implicit s: Scheduler) {

  def subscribe(callback: Update => Unit) = new Subscription {

    private val subscription = observable.subscribe(callback.andThen(_ => Ack.Continue))

    override def unsubscribe(): Unit = subscription.cancel()
  }
}

object ObservableNotifier {

  class ConcurrentPublisher(subject: ConcurrentSubject[Update, Update])(implicit s: Scheduler)
    extends ObservableNotifier(subject) {

    def this()(implicit s: Scheduler) = this(ConcurrentSubject.publish)
  }
}
