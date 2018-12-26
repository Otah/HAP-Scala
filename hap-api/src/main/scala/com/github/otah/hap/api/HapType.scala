package com.github.otah.hap.api

import java.util.{Locale, UUID}

sealed abstract class HapType(val asUUID: UUID) {

  def minimalForm: String
  val asFullUUID: String = asUUID.toString.toUpperCase(Locale.ROOT)
}

object HapType {

  private val appleSuffix = "-0000-1000-8000-0026BB765291"

  private def shortToUuid(shortForm: String) = {
    val zeroFilled: String = shortForm.toUpperCase(Locale.ROOT).reverse.padTo(8, '0').reverse
    UUID.fromString(zeroFilled + appleSuffix)
  }

  final case class Generic(uuid: UUID) extends HapType(uuid) {
    require(!(asFullUUID endsWith appleSuffix), "Generic UUID cannot use Apple namespace")

    override def minimalForm = asFullUUID
  }
  object Generic {
    def apply(uuid: String): Generic = apply(UUID.fromString(uuid))
  }

  final case class Apple(shortForm: String) extends HapType(shortToUuid(shortForm)) {

    override def minimalForm = shortForm
  }
}
