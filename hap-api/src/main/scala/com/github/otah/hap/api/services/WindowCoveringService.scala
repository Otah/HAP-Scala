package com.github.otah.hap.api
package services

import characteristics._

trait WindowCoveringService extends SpecializedService with OptionalName {

  override final val serviceType = hap.service.windowCovering

  val currentPosition: Required[CurrentPositionCharacteristic]
  val targetPosition: Required[TargetPositionCharacteristic]

  val positionState: Required[PositionStateCharacteristic]

  val currentHorizontalTilt: Optional[CurrentHorizontalTiltAngle] = None
  val targetHorizontalTilt: Optional[TargetHorizontalTiltAngle] = None

  override def all: AllSupported = AllSupported(name,
    currentPosition, targetPosition, positionState,
    currentHorizontalTilt, targetHorizontalTilt,
  )
}
