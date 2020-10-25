package com.github.otah.hap.server

import java.io.ByteArrayOutputStream
import java.nio.{ByteBuffer, ByteOrder}
import java.util.concurrent.atomic.AtomicInteger

import io.github.hapjava.server.impl.connections.LPBAProcessorProxy
import io.github.hapjava.server.impl.crypto.{ChachaDecoder, ChachaEncoder}
import org.bouncycastle.util.Pack

/** So far blindly copied from HAP Java, only syntax-rewritten to Scala
  */
class Encryption {

  private val inboundBinaryMessageCount = new AtomicInteger(0)
  private val outboundBinaryMessageCount = new AtomicInteger(0)

  private val lpbaProcessor = new LPBAProcessorProxy

  def encrypt(keys: SessionKeys)(response: Array[Byte]): Array[Byte] = {
    val writeKey = keys.writeKey.toArray
    var offset = 0
    val baos = new ByteArrayOutputStream
    try {
      while (offset < response.length) {
        val length = Math.min(response.length - offset, 0x400).toShort
        val lengthBytes = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(length).array
        baos.write(lengthBytes)
        val nonce = Pack.longToLittleEndian(outboundBinaryMessageCount.getAndIncrement())
        var plaintext =
          if (response.length == length) response
          else {
            val arr = new Array[Byte](length)
            System.arraycopy(response, offset, arr, 0, length)
            arr
          }
        offset += length
        baos.write(new ChachaEncoder(writeKey, nonce).encodeCiphertext(plaintext, lengthBytes))
      }
      baos.toByteArray
    } finally baos.close()
  }

  def decrypt(keys: SessionKeys)(encrypted: Array[Byte]): Array[Byte] = {
    val readKey = keys.readKey.toArray

    lpbaProcessor.extractFrames(encrypted).toArray flatMap { msg =>
      val mac = new Array[Byte](16)
      val ciphertext = new Array[Byte](msg.length - 16)
      System.arraycopy(msg, 0, ciphertext, 0, msg.length - 16)
      System.arraycopy(msg, msg.length - 16, mac, 0, 16)
      val additionalData =
        ByteBuffer.allocate(2)
          .order(ByteOrder.LITTLE_ENDIAN)
          .putShort((msg.length - 16).toShort)
          .array

      val nonce = Pack.longToLittleEndian(inboundBinaryMessageCount.getAndIncrement())
      new ChachaDecoder(readKey, nonce).decodeCiphertext(mac, additionalData, ciphertext)
    }
  }
}
