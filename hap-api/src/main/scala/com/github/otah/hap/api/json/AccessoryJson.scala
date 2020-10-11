package com.github.otah.hap.api.json

import com.github.otah.hap.api.HomeKitAccessory
import sjsonnew.shaded.scalajson.ast._

import scala.concurrent.{ExecutionContext, Future}

object AccessoryJson {

  // TODO check the exact field names

  private def build(what: Seq[Future[JValue]])(f: JArray => Map[String, JValue])(implicit ec: ExecutionContext) =
    Future.sequence(what) map (seq => JObject(f.apply(JArray(seq.toVector))))

  def apply(accessory: HomeKitAccessory)(implicit ec: ExecutionContext): Future[JObject] = {

    val allServices = accessory.services/*:+ infoService*/ //TODO add info service

    val iids = allServices flatMap (s => s.iid +: s.service.characteristics.map(_.iid)) map (_.value)
    val duplicates = iids groupBy identity collect {
      case (iid, occurrences) if occurrences.tail.nonEmpty => s"$iid defined ${occurrences.size}x"
    }
    if (duplicates.nonEmpty) throw new IllegalStateException("Duplicate IIDs found: " + duplicates.mkString(", "))

    val servicesFutJson: Seq[Future[JObject]] = allServices map { instance =>
      import instance._
      build(service.characteristics map (_.asJson)) { characteristics =>
        Map(
          "type" -> JString(service.serviceType.minimalForm),
          "iid" -> JNumber(iid.value),
          "characteristics" -> characteristics
        )
      }
    }

    build(servicesFutJson) { services =>
      Map(
        "aid" -> JNumber(accessory.id),
        "services" -> services
      )
    }
  }
}
