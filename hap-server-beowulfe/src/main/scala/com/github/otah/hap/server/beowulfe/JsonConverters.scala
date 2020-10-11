package com.github.otah.hap.server.beowulfe

import javax.json._
import javax.json.JsonValue.ValueType

import sjsonnew.shaded.scalajson
import scalajson.ast._

import scala.collection.JavaConverters._
import scala.util.Try

/** Mutual conversions between [[javax.json]] and [[scalajson.ast]]
  */
trait JsonConverters {

  def convertJsonToJ(jsonValue: JsonValue): JValue = jsonValue.getValueType match {
    case ValueType.ARRAY => JArray(jsonValue.asInstanceOf[JsonArray].asScala.toVector map convertJsonToJ)
    case ValueType.OBJECT =>
      JObject(
        jsonValue.asInstanceOf[JsonObject].asScala.map { case (key, value) =>
          key -> convertJsonToJ(value)
        }.toMap
      )
    case ValueType.NULL => JNull
    case ValueType.NUMBER => JNumber(jsonValue.toString) getOrElse (throw new IllegalArgumentException(s"$jsonValue is not a number"))
    case ValueType.STRING => JString(jsonValue.asInstanceOf[JsonString].getString)
    case ValueType.FALSE => JFalse
    case ValueType.TRUE => JTrue
  }

  def convertObjectJToJson(jObject: JObject): JsonObject = {
    val builder = Json.createObjectBuilder()
    jObject.value foreach {
      case (name, value) => addJToJson(builder, name, value)
    }
    builder.build()
  }

  def addJToJsonArray(builder: JsonArrayBuilder)(jValue: JValue): Unit = jValue match {
    case JNull =>
      builder.addNull()
    case JTrue =>
      builder.add(true)
    case JFalse =>
      builder.add(false)
    case JString(str) =>
      builder.add(str)
    case JNumber(num) =>
      Try(num.toLong) map builder.add orElse (Try(num.toDouble) map builder.add) getOrElse
        (throw new IllegalStateException(s"Cannot convert $num to a number"))

    case JObject(properties) =>
      val inner = Json.createObjectBuilder()
      properties foreach {
        case (sName, sValue) => addJToJson(inner, sName, sValue)
      }
      builder.add(inner)

    case JArray(list) =>
      val inner = Json.createArrayBuilder()
      list foreach addJToJsonArray(inner)
      builder.add(inner)
  }

  def addJToJson(builder: JsonObjectBuilder, name: String, jValue: JValue): Unit = jValue match {
    case JNull =>
      builder.addNull(name)
    case JTrue =>
      builder.add(name, true)
    case JFalse =>
      builder.add(name, false)
    case JString(str) =>
      builder.add(name, str)
    case JNumber(num) =>
      Try(num.toLong) map (builder.add(name, _)) orElse (Try(num.toDouble) map (builder.add(name, _))) getOrElse
        (throw new IllegalStateException(s"Cannot convert $num to a number"))

    case JObject(properties) =>
      val inner = Json.createObjectBuilder()
      properties foreach {
        case (sName, sValue) => addJToJson(inner, sName, sValue)
      }
      builder.add(name, inner)

    case JArray(list) =>
      val inner = Json.createArrayBuilder()
      list foreach addJToJsonArray(inner)
      builder.add(name, inner)
  }
}

object JsonConverters extends JsonConverters
