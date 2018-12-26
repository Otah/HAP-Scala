package com.github.otah.hap.api.server

import com.github.otah.hap.api.HomeKitAccessory

case class HomeKitRoot(auth: HomeKitAuthentication, root: Either[HomeKitAccessory, HomeKitBridge])

object HomeKitRoot {
  def bridge(bridge: HomeKitBridge, auth: HomeKitAuthentication) = HomeKitRoot(auth, Right(bridge))
  def standaloneAccessory(accessory: HomeKitAccessory, auth: HomeKitAuthentication) = HomeKitRoot(auth, Left(accessory))
}
