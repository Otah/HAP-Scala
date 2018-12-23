package com.github.otah.hap.observable

import com.github.otah.hap.api.Characteristic
import rx.lang.scala.Observable
import rx.lang.scala.subjects.BehaviorSubject

abstract class ObservableCharacteristic[T](observable: Observable[T], currentValue: /*Single*/Observable[T])
  extends Characteristic[T] {

  def this(behavior: BehaviorSubject[T]) = this(behavior, behavior.head)

  override val reader = Reader(currentValue.toBlocking.toFuture)

  override val notifier = Some(ObservableNotifier[T](observable))
}
