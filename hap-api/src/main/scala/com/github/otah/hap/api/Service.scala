package com.github.otah.hap.api

import spray.json.*

import scala.concurrent.{ExecutionContext, Future}

trait Service extends TypeConvenience {

  def iid: InstanceId

  def serviceType: HapType

  def characteristics: Seq[Identified[Characteristic]]

  def characteristicsWrite(x: Map[InstanceId, JsValue])(implicit ec: ExecutionContext): Seq[Future[_]]

  def characteristicsValues()(implicit ec: ExecutionContext): Map[InstanceId, Future[JsValue]]

  def characteristicsValues(ids: Set[InstanceId])(implicit ec: ExecutionContext): Map[InstanceId, Future[JsValue]]

  def characteristicsSubscribe(callback: Map[InstanceId, JsValue] => Unit): Subscription

  protected def characteristicsJson()(implicit ec: ExecutionContext): Future[Seq[JsObject]] = {

    val futureVals = characteristicsValues().toSeq map {
      case (id, futureVal) => futureVal map (id -> _)
    }

    Future.sequence(futureVals) map (_.toMap) map { vals =>
      characteristics map { case (iid, ch) =>
        ch.toJson(iid, vals.getOrElse(iid, JsNull)) //TODO Null might not be the right fallback
      }
    }
  }

  def toJson()(implicit ec: ExecutionContext): Future[JsObject] =
    characteristicsJson() map { ch =>
      JsObject(
        "type" -> JsString(serviceType.minimalForm),
        "iid" -> JsNumber(iid.value),
        "characteristics" -> JsArray(ch.toVector)
      )
    }
}
