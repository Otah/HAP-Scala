package com.github.otah.hap.api.internal

import scalajson.ast.{JObject, JValue}

import scala.concurrent.{ExecutionContext, Future}

trait LowLevelCharacteristic {

  def asJson(instanceId: Int)(implicit ec: ExecutionContext): Future[JObject]

  def readJsonValue()(implicit ec: ExecutionContext): Future[JValue]

  def jsonWriter(implicit ec: ExecutionContext): Option[JValue => Future[_]]

  def jsonValueNotifier(implicit ec: ExecutionContext): Option[LowLevelNotifier]
}
