package com.github.otah.hap.server

case class TypeAndValue(typeByte: Byte, value: Seq[Byte]) {

  lazy val valueUnsigned: Seq[Int] = value map TypeAndValue.byteAsUnsigned
}

object TypeAndValue {
  def byteAsUnsigned(byte: Byte): Int = byte & 0xff
}
