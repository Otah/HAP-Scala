package com.github.otah.hap.server

import akka.util.ByteString

sealed abstract class HttpBytes {

  def bytes: ByteString
  def encodeResponse: ByteString => ByteString
}

object HttpBytes {

  case class Plain(bytes: ByteString) extends HttpBytes {
    override def encodeResponse: ByteString => ByteString = identity
  }

  case class Encrypted(bytes: ByteString, encodeResponse: ByteString => ByteString) extends HttpBytes
}
