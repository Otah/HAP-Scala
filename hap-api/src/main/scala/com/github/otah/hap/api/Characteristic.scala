package com.github.otah.hap.api

import spray.json._

import scala.concurrent.{ExecutionContext, Future}

trait Characteristic[T] extends LowLevelCharacteristic with TypeConvenience {

  def characteristicType: HapType
  def description: String

  def format: String

  type Reader = ExecutionContext => Future[Option[T]]
  type Writer = ExecutionContext => T => Future[_]
  type Notifier = TypedNotifier[T]

  def reader: Option[Reader]
  def writer: Option[Writer]
  def notifier: Option[Notifier]

  object Reader {
    def apply(x: => Future[T]): Some[Reader] = Some { implicit ec: ExecutionContext =>
      x map Some.apply
    }
    def withExecutor(x: ExecutionContext => Future[T]): Some[Reader] = Some { implicit ec: ExecutionContext =>
      x(ec) map Some.apply
    }
    def Null: Some[Reader] = Some(_ => Future.successful(None))
  }

  object Writer {
    def apply(x: T => Future[_]): Some[Writer] = Some(_ => x)
    def withExecutor(x: ExecutionContext => T => Future[_]): Some[Writer] = Some(x)
  }

  protected def toJsonValue(v: T): JsValue
  protected def fromJsonValue(jv: JsValue): T

  override def readJsonValue()(implicit ec: ExecutionContext) = reader match {
    case Some(create) => create(ec) map (_ map toJsonValue getOrElse JsNull)
    case None => Future.successful(JsNull)
  }

  override def jsonWriter(implicit ec: ExecutionContext): Option[JsValue => Future[_]] = writer map { f =>
    newValue => f(ec)(fromJsonValue(newValue))
  }

  override def jsonValueNotifier(implicit ec: ExecutionContext): Option[LowLevelNotifier] = notifier map { n => new LowLevelNotifier {

    override def subscribe(callback: JsValue => Future[Unit]) = n.subscribe(newValue => callback(toJsonValue(newValue)))
  }}

  override def asJson(instanceId: Int)(implicit ec: ExecutionContext): Future[JsObject] = readJsonValue() map { currentValue =>
    val perms = reader.map(_ => "pr") ++ writer.map(_ => "pw") ++ notifier.map(_ => "ev")
    JsObject(
      "iid" -> JsNumber(instanceId),
      "type" -> JsString(characteristicType.minimalForm),
      "format" -> JsString(format),
      "value" -> currentValue,
      "perms" -> JsArray(perms.toVector map JsString.apply),
      "description" -> JsString(description),
      "ev" -> JsFalse,
    )
  }
}
