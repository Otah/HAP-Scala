package com.github.otah
package hap
package api
package server

class HomeKitRoot private (
  val auth: HomeKitAuthentication,
  val rootDevice: Either[HomeKitAccessory, HomeKitBridge],
  val label: String,
  val category: Int,
  val configurationNumber: Int,
  val accessories: Seq[Identified[HomeKitAccessory]]
)

object HomeKitRoot {
  //TODO add real category

  def bridge(bridge: HomeKitBridge, label: String, configuration: Int = 1)(implicit auth: HomeKitAuthentication) =
    new HomeKitRoot(auth, Right(bridge), label, category = 1, configuration, bridge.selfAndAccessories)

  def standaloneAccessory(accessory: HomeKitAccessory, label: String, configuration: Int = 1)(implicit auth: HomeKitAuthentication) =
    new HomeKitRoot(auth, Left(accessory), label, category = 1, configuration, Seq(1 identifying accessory))
}
