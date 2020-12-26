package com.github.otah.hap.api.services

import com.github.otah.hap.api._

import scala.language.implicitConversions

trait HigherKindService extends AccessoryService {

  type Required[+A <: LowLevelCharacteristic]
  type Optional[+A <: LowLevelCharacteristic] = Option[Required[A]]
  type Options = Seq[Option[Required[LowLevelCharacteristic]]] //TODO rename Options to something more specific

  def Options(opts: Option[Required[LowLevelCharacteristic]]*): Options = opts

  def options: Options
}

object HigherKindService {

  trait Explicit extends HigherKindService {

    override type Required[+A] = Identified[A]

    implicit def anyToOption[A](any: Identified[A]): Option[Identified[A]] = Some(any)

    override def characteristics: Characteristics = options.flatten
  }

  trait Automatic extends HigherKindService {

    def baseInstanceId: Int

    override type Required[+A] = () => A

    implicit def anyToSelf[A](any: A): () => A = () => any
    implicit def anyToOption[A](any: A): Option[() => A] = Some(() => any)

    override def characteristics: Characteristics = options.zipWithIndex collect {
      case (Some(ch), id) => (baseInstanceId + 1 + id) identifying ch()
    }
  }
}
