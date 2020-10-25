package io.github.hapjava.server.impl.connections

import scala.collection.JavaConverters.collectionAsScalaIterableConverter

class LPBAProcessorProxy {

  private val proxied = new LengthPrefixedByteArrayProcessor

  def extractFrames(data: Array[Byte]): Iterable[Array[Byte]] = proxied.handle(data).asScala
}
