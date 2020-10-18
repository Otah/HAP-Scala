package io.github.hapjava.server.impl.pairing

class SrpHandlerProxy private(private val srpHandler: SrpHandler) {

  def K() = srpHandler.getK

  def responseFor(message: Array[Byte]): Array[Byte] = {
    srpHandler.handle(PairSetupRequest.of(message)).getBody.array()
  }
}

object SrpHandlerProxy {
  def apply(pin: String, salt: BigInt): SrpHandlerProxy = new SrpHandlerProxy(new SrpHandler(pin, salt.bigInteger))
}
