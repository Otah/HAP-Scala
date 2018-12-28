package com.github.otah.hap.api

case class InstanceId(value: Int) {
  require(value > 0, "IID has to be positive number")

  def -(service: AccessoryService): ServiceInstance = ServiceInstance(this, service)
  def -(ch: LowLevelCharacteristic): CharacteristicInstance = CharacteristicInstance(this, Some(ch))
  def -(maybeCh: Option[LowLevelCharacteristic]): CharacteristicInstance = CharacteristicInstance(this, maybeCh)
}
