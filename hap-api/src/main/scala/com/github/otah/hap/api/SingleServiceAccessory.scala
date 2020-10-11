package com.github.otah.hap.api

import com.github.otah.hap.api.services.BatteryService

/** Many HAP accessories are defined only with a single service.
  * Mix this trait in case you want to define the service characteristics directly in the accessory class,
  * instead of nesting the service as an instance in a field.
  *
  * This also defines the [[HomeKitAccessory.services]] to a sequence containing `this`
  */
trait SingleServiceAccessory extends HomeKitAccessory {

  this: AccessoryService =>

  def baseInstanceId = 30 // keeps enough space for Accessory Information service (IID 1) and its characteristics

  def services: Seq[ServiceInstance] = Seq(InstanceId(baseInstanceId) -> this) ++
    (InstanceId(20) -> battery)

  def battery: Option[BatteryService] = None
}
