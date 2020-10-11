package com.github.otah.hap.api

import sjsonnew.shaded.scalajson.ast.JObject

import scala.concurrent.{ExecutionContext, Future}

class CharacteristicInstance(val iid: InstanceId, val characteristic: LowLevelCharacteristic) {

  def asJson(implicit ec: ExecutionContext): Future[JObject] = characteristic.asJson(iid.value)
}
