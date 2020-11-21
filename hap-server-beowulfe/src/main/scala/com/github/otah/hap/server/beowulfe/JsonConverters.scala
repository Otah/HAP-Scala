package com.github.otah.hap.server.beowulfe

import javax.json._
import javax.json.JsonValue.ValueType

import spray.json._

import scala.collection.JavaConverters._
import scala.util.Try

/** Mutual conversions between [[javax.json]] and [[spray.json]]
  */
trait JsonConverters {

  def convertJsonToJ(jsonValue: JsonValue): JsValue = jsonValue.getValueType match {
    case ValueType.ARRAY => JsArray(jsonValue.asInstanceOf[JsonArray].asScala.toVector map convertJsonToJ)
    case ValueType.OBJECT =>
      JsObject(
        jsonValue.asInstanceOf[JsonObject].asScala.map { case (key, value) =>
          key -> convertJsonToJ(value)
        }.toMap
      )
    case ValueType.NULL => JsNull
    case ValueType.NUMBER => JsNumber(jsonValue.toString)
    case ValueType.STRING => JsString(jsonValue.asInstanceOf[JsonString].getString)
    case ValueType.FALSE => JsFalse
    case ValueType.TRUE => JsTrue
  }

  def convertObjectJToJson(jObject: JsObject): JsonObject = {
    val builder = Json.createObjectBuilder()
    jObject.fields foreach {
      case (name, value) => addJToJson(builder, name, value)
    }
    builder.build()
  }

  def addJToJsonArray(builder: JsonArrayBuilder)(jValue: JsValue): Unit = jValue match {
    case JsNull =>
      builder.addNull()
    case JsTrue =>
      builder.add(true)
    case JsFalse =>
      builder.add(false)
    case JsString(str) =>
      builder.add(str)
    case JsNumber(num) =>
      builder.add(num.bigDecimal)

    case JsObject(properties) =>
      val inner = Json.createObjectBuilder()
      properties foreach {
        case (sName, sValue) => addJToJson(inner, sName, sValue)
      }
      builder.add(inner)

    case JsArray(list) =>
      val inner = Json.createArrayBuilder()
      list foreach addJToJsonArray(inner)
      builder.add(inner)
  }

  def addJToJson(builder: JsonObjectBuilder, name: String, jValue: JsValue): Unit = jValue match {
    case JsNull =>
      builder.addNull(name)
    case JsTrue =>
      builder.add(name, true)
    case JsFalse =>
      builder.add(name, false)
    case JsString(str) =>
      builder.add(name, str)
    case JsNumber(num) =>
      builder.add(name, num.bigDecimal)

    case JsObject(properties) =>
      val inner = Json.createObjectBuilder()
      properties foreach {
        case (sName, sValue) => addJToJson(inner, sName, sValue)
      }
      builder.add(name, inner)

    case JsArray(list) =>
      val inner = Json.createArrayBuilder()
      list foreach addJToJsonArray(inner)
      builder.add(name, inner)
  }
}

object JsonConverters extends JsonConverters
