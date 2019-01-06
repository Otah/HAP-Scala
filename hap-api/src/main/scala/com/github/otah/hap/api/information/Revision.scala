package com.github.otah.hap.api.information

import scala.util.Try

case class Revision(asString: String) {
  require(
    {
      val parts = asString.split('.').toSeq
      parts.size <= 3 && parts.forall(s => Try(s.toInt).isSuccess)
    },
    s"""The revision "$asString" does not comply with required format N[.N[.N]]"""
  )
}
