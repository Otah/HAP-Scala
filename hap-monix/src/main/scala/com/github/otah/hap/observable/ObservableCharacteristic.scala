package com.github.otah.hap.observable

import com.github.otah.hap.api.Characteristic
import monix.eval.Task
import monix.execution.Scheduler
import monix.reactive.Observable
import monix.reactive.subjects.BehaviorSubject

abstract class ObservableCharacteristic[T](observable: Observable[T], currentValue: Task[T])
                                          (implicit scheduler: Scheduler) extends Characteristic[T] {

  def this(behavior: BehaviorSubject[T])(implicit scheduler: Scheduler) = this(behavior, behavior.headL)

  override val reader = Reader(currentValue.runAsync)

  override val notifier = Some(ObservableNotifier[T](observable))
}
