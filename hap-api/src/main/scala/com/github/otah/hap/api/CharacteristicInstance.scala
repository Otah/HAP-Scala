package com.github.otah.hap.api

import sjsonnew.shaded.scalajson.ast.JObject

import scala.concurrent.{ExecutionContext, Future}

case class CharacteristicInstance(iid: InstanceId, characteristic: Option[LowLevelCharacteristic]) {

  def asJson(implicit ec: ExecutionContext): Option[Future[JObject]] = characteristic map (_.asJson(iid.value))
}
