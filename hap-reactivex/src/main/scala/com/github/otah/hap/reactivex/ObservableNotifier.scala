package com.github.otah.hap.reactivex

import com.github.otah.hap.api.{Subscription, TypedNotifier}
import rx.lang.scala.Observable

import scala.concurrent.Future

class ObservableNotifier[T](observable: Observable[T]) extends TypedNotifier[T] {

  override def subscribe(callback: T => Future[Unit]) = new Subscription {

    private val subscription = observable.subscribe(callback.andThen(_ => ()))

    override def unsubscribe(): Unit = subscription.unsubscribe()
  }
}

object ObservableNotifier {
  def apply[T](observable: Observable[T]): Some[ObservableNotifier[T]] = Some(new ObservableNotifier(observable))
}
