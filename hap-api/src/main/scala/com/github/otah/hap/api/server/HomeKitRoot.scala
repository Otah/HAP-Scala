package com.github.otah
package hap
package api
package server

class HomeKitRoot private (
  val auth: HomeKitAuthentication,
  val rootDevice: Either[HomeKitAccessory, HomeKitBridge],
  val label: String,
  val category: Int,
  val accessories: Seq[Identified[HomeKitAccessory]]
)

object HomeKitRoot {
  //TODO add real category

  def bridge(bridge: HomeKitBridge, label: String)(implicit auth: HomeKitAuthentication) =
    new HomeKitRoot(auth, Right(bridge), label, category = 1, bridge.selfAndAccessories)

  def standaloneAccessory(accessory: HomeKitAccessory, label: String)(implicit auth: HomeKitAuthentication) =
    new HomeKitRoot(auth, Left(accessory), label, category = 1, Seq(1 identifying accessory))
}
