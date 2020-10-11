package com.github.otah.hap.server.beowulfe

import java.util.concurrent.CompletableFuture

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions
import scala.util.{Failure, Success}

/** This trait allows to mixin implicit conversions from Scala [[Future]] into Java [[CompletableFuture]]
  * required in Beowulfe's Java implementation.
  */
trait AccessoryConversions {

  implicit def futToCpltFuture[T](future: Future[T])(implicit ec: ExecutionContext): CompletableFuture[T] = {
    val jFuture = new CompletableFuture[T]()
    future.onComplete {
      case Success(result) => jFuture.complete(result)
      case Failure(ex) => jFuture.completeExceptionally(ex)
    }
    jFuture
  }

  implicit def futOfBoolToCpltFuture(future: Future[Boolean])(implicit ec: ExecutionContext): CompletableFuture[java.lang.Boolean] =
    future map Boolean.box

  implicit def futOfIntToCpltFuture(future: Future[Int])(implicit ec: ExecutionContext): CompletableFuture[java.lang.Integer] =
    future map Integer.valueOf

  implicit def futToVoidCpltFuture(future: Future[_])(implicit ec: ExecutionContext): CompletableFuture[java.lang.Void] =
    futToCpltFuture(future map (_ => null: Void))
}
