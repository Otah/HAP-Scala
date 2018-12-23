package com.github.otah.hap.server.beowulfe

import com.beowulfe.hap.HomekitCharacteristicChangeCallback
import com.beowulfe.hap.accessories.Switch

import scala.concurrent.{ExecutionContext, Future}

case class BeowulfeSwitch(id: Int, label: String)(implicit ec: ExecutionContext) extends Switch with AccessoryConversions {

  @volatile private var changeCallback: Option[HomekitCharacteristicChangeCallback] = None
  @volatile private var state = false

  def changed(): Unit = changeCallback.foreach(_.changed())

  override def setSwitchState(state: Boolean) = {
    this.state = state
    changed()
    println(s"Current state: $state")
    Future.unit
  }

  override def getSwitchState = Future.successful(state)

  override def subscribeSwitchState(callback: HomekitCharacteristicChangeCallback): Unit = changeCallback = Some(callback)

  override def unsubscribeSwitchState(): Unit = changeCallback = None

  override def getLabel = label

  override def getId = id

  override def getManufacturer = "Otah"

  override def getSerialNumber = "none"

  override def getModel = "none"

  override def identify(): Unit = println("This is the original switch")
}
