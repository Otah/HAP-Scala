package com.github.otah.hap.api.accessories

import com.github.otah.hap.api._
import com.github.otah.hap.api.services._
import com.github.otah.hap.api.{AccessoryService, HomeKitAccessory}

/** Many HAP accessories are defined only with a single service.
  * Mix this trait in case you want to define the service characteristics directly in the accessory class,
  * instead of nesting the service as an instance in a field.
  *
  * This also defines the [[HomeKitAccessory.services]] to a sequence containing `this`
  */
trait SingleServiceAccessory extends HomeKitAccessory {

  this: AccessoryService =>

  def baseInstanceId = 30 // keeps enough space for Accessory Information service (IID 1) and its characteristics

  def services: Seq[Identified[AccessoryService]] = Seq(InstanceId(baseInstanceId) -> this) ++ battery

  def battery: Option[Identified[BatteryService]] = None
}
