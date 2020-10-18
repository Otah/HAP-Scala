package com.github.otah.hap.server

import com.github.otah.hap.api.server.HomeKitAuthentication
import com.github.otah.hap.server.beowulfe.BeowulfeAuthInfoAdapter
import io.github.hapjava.server.impl.HomekitRegistry
import io.github.hapjava.server.impl.http.{HttpMethod, HttpRequest}
import io.github.hapjava.server.impl.pairing.{PairVerificationManager, UpgradeResponse}

class PairVerifyHandler(auth: HomeKitAuthentication, label: String) {

  private val mgr = new PairVerificationManager(new BeowulfeAuthInfoAdapter(auth), new HomekitRegistry(label))

  def respondTo(message: TlvMessage) = {

    val origResponse = mgr.handle(new HttpRequest {
      override def getUri: String = "/pair-verify" // but can be anything, only body is read
      override def getBody: Array[Byte] = message.asBytes.toArray
      override def getMethod: HttpMethod = HttpMethod.POST
    })

    val maybeKeys = Some(origResponse) collect {
      case upgraded: UpgradeResponse => SessionKeys(upgraded.getReadKey.array(), upgraded.getWriteKey.array())
    }

    TlvMessage(origResponse.getBody.array()) -> maybeKeys
  }
}
