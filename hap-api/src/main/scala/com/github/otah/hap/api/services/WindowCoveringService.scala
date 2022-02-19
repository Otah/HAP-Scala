package com.github.otah.hap.api
package services

import characteristics._

trait WindowCoveringService extends SpecializedService with OptionalName {

  override final val serviceType = hap.service.windowCovering

  def currentPosition: Required[CurrentPositionCharacteristic]
  def targetPosition: Required[TargetPositionCharacteristic]
  
  def positionState: Required[PositionStateCharacteristic]

  override def all: AllSupported = AllSupported(name,
    currentPosition, targetPosition, positionState,
  )
}
