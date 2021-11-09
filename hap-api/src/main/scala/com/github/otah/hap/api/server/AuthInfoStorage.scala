package com.github.otah.hap.api.server

import scala.concurrent.Future

trait AuthInfoStorage {

  def add(username: String, publicKey: Seq[Byte]): Future[_]

  def remove(username: String): Future[_]

  def getPublicKey(username: String): Future[Option[Seq[Byte]]]

  //noinspection AccessorLikeMethodIsEmptyParen
  def isEmpty(): Future[Boolean]
}
