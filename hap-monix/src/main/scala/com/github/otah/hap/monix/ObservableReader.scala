package com.github.otah.hap.monix

import monix.eval.Task
import monix.execution.Scheduler

import scala.concurrent.{ExecutionContext, Future}

object ObservableReader {

  def apply[T](currentValue: Task[T])(implicit scheduler: Scheduler): Some[ExecutionContext => Future[Option[T]]] =
    Some(_ => currentValue.map(Some.apply).runToFuture)
}
