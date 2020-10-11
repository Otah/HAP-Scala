package com.github.otah.hap.api

case class InstanceId(value: Int) {
  require(value > 0, "IID has to be positive number")

  def ->(service: AccessoryService): ServiceInstance = new ServiceInstance(this, service)
  def ->(ch: LowLevelCharacteristic): CharacteristicInstance = new CharacteristicInstance(this, ch)
  def ->(maybeCh: Option[LowLevelCharacteristic]): Option[CharacteristicInstance] = maybeCh map (new CharacteristicInstance(this, _))
}
