package io.github.hapjava.server.impl.pairing

import com.github.otah.hap.api.server.HomeKitAuthentication
import com.github.otah.hap.server.TlvMessage
import com.github.otah.hap.server.beowulfe.BeowulfeAuthInfoAdapter

/** This has to be in "foreign" package to be able to access the SrpHandler
  */
class PairSetupHandlerProxy(auth: HomeKitAuthentication) {

  private val info = new BeowulfeAuthInfoAdapter(auth)

  private val srpHandler: SrpHandler = new SrpHandler(info.getPin, info.getSalt)

  def K() = srpHandler.getK

  def respondTo(message: TlvMessage): TlvMessage = {

    val handler =
      if (message.firstOfType(6).flatMap(_.value.headOption).contains(5)) {
        new FinalPairHandler(K(), info).handle _
      } else {
        srpHandler.handle _
      }

    TlvMessage(
      handler(PairSetupRequest.of(message.asBytes.toArray)).getBody.array()
    )
  }
}
