package com.github.otah.hap.api

import java.util.{Locale, UUID}

sealed abstract class HapId(val asUUID: UUID) {

  def minimalForm: String
  val asFullUUID: String = asUUID.toString.toUpperCase(Locale.ROOT)
}

object HapId {

  private val appleSuffix = "-0000-1000-8000-0026BB765291"

  private def shortToUuid(shortId: String) = {
    val zeroFilled: String = shortId.toUpperCase(Locale.ROOT).reverse.padTo(8, '0').reverse
    UUID.fromString(zeroFilled + appleSuffix)
  }

  final case class Generic(id: UUID) extends HapId(id) {
    require(!(asFullUUID endsWith appleSuffix), "Generic UUID cannot use Apple namespace")

    override def minimalForm = asFullUUID
  }
  object Generic {
    def apply(id: String): Generic = apply(UUID.fromString(id))
  }

  final case class Apple(shortId: String) extends HapId(shortToUuid(shortId)) {

    override def minimalForm = shortId
  }
}
