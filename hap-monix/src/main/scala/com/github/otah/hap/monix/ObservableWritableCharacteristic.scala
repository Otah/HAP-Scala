package com.github.otah.hap.monix

import monix.eval.Task
import monix.execution.Scheduler
import monix.reactive.subjects.{BehaviorSubject, PublishSubject}
import monix.reactive.{Observable, Observer}

import scala.concurrent.Future

abstract class ObservableWritableCharacteristic[T](observable: Observable[T], currentValue: Task[T], setFunc: T => Future[_])
                                                  (implicit scheduler: Scheduler)
        extends ObservableCharacteristic[T](observable, currentValue) {

  def this(observable: Observable[T], currentValue: Task[T], observer: Observer[T])(implicit scheduler: Scheduler) = this(
    observable, currentValue, (v: T) => observer.onNext(v)
  )

  def this(subject: BehaviorSubject[T])(implicit scheduler: Scheduler) = this(subject, subject.headL, subject)

  def this(subject: PublishSubject[T], currentValue: Task[T])(implicit scheduler: Scheduler) = this(subject, currentValue, subject)

  override def writer = Writer(setFunc)
}
