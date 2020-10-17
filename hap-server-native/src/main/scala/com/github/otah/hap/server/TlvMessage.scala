package com.github.otah.hap.server

case class TlvMessage(bytes: Seq[Byte]) {

  val chunks: Seq[TypeAndValue] = {
    def popValue(initial: Seq[Byte]): Option[(TypeAndValue, Seq[Byte])] = initial match {
      case Nil => None
      case Seq(onlyType) => Some(TypeAndValue(onlyType, Nil) -> Nil)
      case moreBytes => Some {
        val lengthAndData = moreBytes.tail
        val dataLength = lengthAndData.head
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

  def firstOfType(id: Byte): Option[TypeAndValue] = chunks.find(_.typeByte == id)
}
