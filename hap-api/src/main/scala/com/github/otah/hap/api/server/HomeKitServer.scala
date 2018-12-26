package com.github.otah.hap.api.server

case class HomeKitServer(port: Int, host: Option[String] = None)(val bridgesOrAccessories: HomeKitRoot*) {
}
