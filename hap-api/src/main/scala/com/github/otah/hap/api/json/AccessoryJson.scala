package com.github.otah.hap.api.json

import com.github.otah.hap.api.HomeKitAccessory
import scalajson.ast._

import scala.concurrent.{ExecutionContext, Future}

object AccessoryJson {

  // TODO check the exact field names

  private def JSeq(seq: Seq[JValue]) = JArray(seq.toVector)
  private def build(what: Seq[Future[JValue]])(f: JArray => Map[String, JValue])(implicit ec: ExecutionContext) =
    Future.sequence(what) map JSeq map f map JObject.apply

  def apply(accessory: HomeKitAccessory)(implicit ec: ExecutionContext): Future[JObject] = {

    val servicesFuts: Seq[Future[JObject]] = accessory.services map { instance =>
      import instance._
      build(service.characteristics flatMap (_.asJson)) { characteristics =>
        Map(
          "type" -> JString(service.serviceType.minimalForm),
          "iid" -> JNumber(iid.value),
          "characteristics" -> characteristics
        )
      }
    }

    build(servicesFuts/*:+ infoService*/) { services => //TODO add info service
      Map(
        "aid" -> JNumber(accessory.id),
        "services" -> services
      )
    }
  }
}
