package com.github.otah.hap.api.services

import com.github.otah.hap.api._

import scala.language.implicitConversions

trait HigherKindService extends AccessoryService {

  type Req[+A <: LowLevelCharacteristic]
  type Opt[+A <: LowLevelCharacteristic] = Option[Req[A]]
  type Options = Seq[Option[Req[LowLevelCharacteristic]]]

  def options: Options
}

object HigherKindService {

  trait Explicit extends HigherKindService {

    override type Req[+A] = Identified[A]

    implicit def anyToOption[A](any: Identified[A]): Option[Identified[A]] = Some(any)

    override def characteristics: Characteristics = options.flatten
  }

  trait Automatic extends HigherKindService {

    def baseInstanceId: Int

    override type Req[+A] = () => A

    implicit def anyToSelf[A](any: A): () => A = () => any
    implicit def anyToOption[A](any: A): Option[() => A] = Some(() => any)

    override def characteristics: Characteristics = options.zipWithIndex collect {
      case (Some(ch), id) => (baseInstanceId + 1 + id) --> ch()
    }
  }
}
