package com.github.otah.hap.api.server

import com.github.otah.hap.api.HomeKitAccessory

class HomeKitRoot private (val auth: HomeKitAuthentication,
                           val root: Either[HomeKitAccessory, HomeKitBridge],
                           val accessories: Seq[HomeKitAccessory])

object HomeKitRoot {

  def bridge(bridge: HomeKitBridge, auth: HomeKitAuthentication) =
    new HomeKitRoot(auth, Right(bridge), bridge.selfAndAccessories)

  def standaloneAccessory(accessory: HomeKitAccessory, auth: HomeKitAuthentication) =
    new HomeKitRoot(auth, Left(accessory), Seq(accessory))
}
