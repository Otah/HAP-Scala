package com.github.otah
package hap.monix

import hap.api.*
import monix.execution.{Ack, Scheduler}
import monix.reactive.Observable
import spray.json.JsValue

trait ObservableService {
  this: Service =>

  protected def notifier: ObservableNotifier

  override def characteristicsSubscribe(callback: Update => Unit): Subscription =
    notifier.subscribe(callback)
}

object ObservableService {

  trait WithPublisher extends ObservableService {
    this: Service =>

    override protected def notifier: ObservableNotifier.ConcurrentPublisher
  }
}
