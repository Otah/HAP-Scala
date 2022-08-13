package com.github.otah.hap.api

import spray.json._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

trait TypedCharacteristic[T] extends Characteristic {

  def toJsonValue(value: T): JsValue

  def fromJsonValue(json: JsValue): T

  def toJson(value: T): JsObject = toJson(toJsonValue(value)) //FIXME handle conversion errors?

  def withValue(eventualValue: Future[T])(implicit ec: ExecutionContext): Identified[Future[JsValue]] =
    iid -> eventualValue.map(toJsonValue)

  def withValue(value: T): Identified[JsValue] =
    iid -> toJsonValue(value)
}
