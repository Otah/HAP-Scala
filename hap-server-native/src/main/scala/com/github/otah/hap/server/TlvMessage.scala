package com.github.otah.hap.server

case class TlvMessage(chunks: Seq[TypeAndValue]) {

  def firstOfType(id: Byte): Option[TypeAndValue] = chunks.find(_.typeByte == id)

  lazy val asBytes: Seq[Byte] = chunks flatMap { tv =>
    tv.typeByte +: tv.value.size.toByte +: tv.value
  }
}

object TlvMessage {

  def parseBytes(bytes: Seq[Byte]): Seq[TypeAndValue] = {
    def popValue(initial: Seq[Byte]): Option[(TypeAndValue, Seq[Byte])] = initial match {
      case Nil => None
      case Seq(onlyType) => Some(TypeAndValue(onlyType, Nil) -> Nil)
      case moreBytes => Some {
        val lengthAndData = moreBytes.tail
        val dataLength = TypeAndValue.byteAsUnsigned(lengthAndData.head)
        val dataAndNext = lengthAndData.tail
        TypeAndValue(moreBytes.head, dataAndNext take dataLength) -> (dataAndNext drop dataLength)
      }
    }

    var current = bytes

    Stream.continually {
      popValue(current) map {
        case (tv, remaining) =>
          current = remaining
          tv
      }
    }.takeWhile(_.nonEmpty).flatten
  }

  def apply(bytes: Array[Byte]): TlvMessage = apply(parseBytes(bytes.toSeq))
}
