package com.github.otah.hap.api.server

import com.github.otah.hap.api._

class HomeKitRoot private (
  val auth: HomeKitAuthentication,
  val rootDevice: Either[HomeKitAccessory, HomeKitBridge],
  val label: String,
  val accessories: Seq[Identified[HomeKitAccessory]]
)

object HomeKitRoot {

  def bridge(bridge: HomeKitBridge, label: String)(implicit auth: HomeKitAuthentication) =
    new HomeKitRoot(auth, Right(bridge), label, bridge.selfAndAccessories)

  def standaloneAccessory(accessory: HomeKitAccessory, label: String)(implicit auth: HomeKitAuthentication) =
    new HomeKitRoot(auth, Left(accessory), label, Seq(1 <=> accessory))
}
