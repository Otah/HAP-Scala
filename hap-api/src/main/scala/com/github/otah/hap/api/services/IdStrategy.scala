package com.github.otah.hap.api.services

import com.github.otah.hap.api._

import scala.language.implicitConversions

object IdStrategy {

  trait Explicit {
    this: SpecializedService =>

    override type Required[+A] = Identified[A]

    implicit def anyToOption[A](any: Identified[A]): Option[Identified[A]] = Some(any)

    override def characteristics: Characteristics = all.flatten

    override def getCharacteristic[A <: Characteristic](required: Required[A]): A =
      required.characteristic

    override def getCharacteristic[A <: Characteristic](optional: Optional[A]): Option[A] =
      optional map (_.characteristic)
  }

  trait Automatic {
    this: SpecializedService =>

    override type Required[+A] = () => A

    implicit def anyToSelf[A](any: A): () => A = () => any
    implicit def anyToOption[A](any: A): Option[() => A] = Some(() => any)

    override def characteristics: Characteristics = all.zipWithIndex collect {
      case (Some(ch), id) => (iid + 1 + id) identifying ch()
    }

    override def getCharacteristic[A <: Characteristic](required: Required[A]): A =
      required()

    override def getCharacteristic[A <: Characteristic](optional: Optional[A]): Option[A] =
      optional.map(_.apply())
  }
}
