package com.github.otah.hap.observable

import rx.lang.scala.subjects.BehaviorSubject
import rx.lang.scala.{Observable, Observer, Subject}

import scala.concurrent.Future

abstract class ObservableWritableCharacteristic[T](observable: Observable[T], currentValue: Observable[T], setFunc: T => Future[_])
        extends ObservableCharacteristic[T](observable, currentValue) {

  def this(observable: Observable[T], currentValue: Observable[T], observer: Observer[T]) = this(
    observable,
    currentValue,
    (v: T) => {
      observer.onNext(v)
      Future.unit
    }
  )

  def this(subject: BehaviorSubject[T]) = this(subject, subject.head, subject)

  def this(subject: Subject[T], currentValue: Observable[T]) = this(subject, currentValue, subject)

  override def writer = Writer(setFunc)
}
