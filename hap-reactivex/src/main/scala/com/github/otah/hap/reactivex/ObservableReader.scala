package com.github.otah.hap.reactivex

import rx.lang.scala.Observable

import scala.concurrent.{ExecutionContext, Future}

object ObservableReader {

  def apply[T](currentValue: Observable[T]): Some[ExecutionContext => Future[Option[T]]] =
    Some(_ => currentValue.map(Some.apply).toBlocking.toFuture)
}
