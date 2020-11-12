package com.github.otah.hap.api.json

import com.github.otah.hap.api._
import sjsonnew.shaded.scalajson.ast._

import scala.concurrent.{ExecutionContext, Future}

object AccessoryJson {

  // TODO check the exact field names

  private def build(what: Seq[Future[JValue]])(f: JArray => Map[String, JValue])(implicit ec: ExecutionContext) =
    Future.sequence(what) map (seq => JObject(f.apply(JArray(seq.toVector))))

  def apply(accessory: Identified[HomeKitAccessory])(implicit ec: ExecutionContext): Future[JObject] = {

    val (aid, acc) = accessory

    val allServices = acc.services/*:+ infoService*/ //TODO add info service

    val iids = allServices flatMap {
      case (serviceId, service) =>
        val characteristicsIds = service.characteristics map {
          case (characteristicId, _) => characteristicId
        }
        serviceId +: characteristicsIds
    }
    val duplicates = iids map (_.value) groupBy identity collect {
      case (iid, occurrences) if occurrences.tail.nonEmpty => s"$iid defined ${occurrences.size}x"
    }
    if (duplicates.nonEmpty) throw new IllegalStateException("Duplicate IIDs found: " + duplicates.mkString(", "))

    val servicesFutJson: Seq[Future[JObject]] = allServices map { case (serviceId, service) =>
      val xxx = service.characteristics map {
        case (characteristicId, characteristic) => characteristic.asJson(characteristicId.value) // TODO glue IID here instead of in asJson?
      }
      build(xxx) { characteristics =>
        Map(
          "type" -> JString(service.serviceType.minimalForm),
          "iid" -> JNumber(serviceId.value),
          "characteristics" -> characteristics
        )
      }
    }

    build(servicesFutJson) { services =>
      Map(
        "aid" -> JNumber(aid.value),
        "services" -> services
      )
    }
  }
}
