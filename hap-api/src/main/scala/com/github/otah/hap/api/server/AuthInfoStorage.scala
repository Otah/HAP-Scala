package com.github.otah.hap.api.server

import java.util.concurrent.ConcurrentHashMap

import scala.jdk.CollectionConverters._

//TODO consider async
trait AuthInfoStorage {

  def add(username: String, publicKey: Array[Byte]): Unit = add(username, publicKey.toSeq)
  def add(username: String, publicKey: Seq[Byte]): Unit

  def remove(username: String): Unit

  def get(username: String): Option[Seq[Byte]]
  def getAsArray(username: String): Option[Array[Byte]] = get(username) map (_.toArray)

  def isEmpty: Boolean
}

object AuthInfoStorage {

  type Key = Seq[Byte]
  type UserKeys = Map[String, Key]

  /** Constructs a convenience in-memory auth storage which can be persisted
    * (and loaded from persistence) using the provided arguments.
    * @param initialUserKeys User keys map for initial data, e.g. loaded persisted data.
    * @param onChange Callback which allows to store the (complete) current state of user keys,
    *                 or anyhow react to a change.
    * @return
    */
  def inMemory(initialUserKeys: UserKeys = Map.empty, onChange: (() => UserKeys) => Unit = _ => {}): AuthInfoStorage = new AuthInfoStorage {

    private val userKeys = new ConcurrentHashMap[String, Seq[Byte]](initialUserKeys.asJava)

    private def snapshotUserKeys(): UserKeys = userKeys.asScala.toMap

    private def changed(any: Unit): Unit = onChange(snapshotUserKeys _)

    override def add(username: String, publicKey: Key): Unit = changed(userKeys.put(username, publicKey))

    override def remove(username: String): Unit = changed(userKeys.remove(username))

    override def get(username: String): Option[Key] = Option(userKeys.get(username))

    override def isEmpty: Boolean = userKeys.isEmpty
  }
}
