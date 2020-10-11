package com.github.otah.hap.server.beowulfe

import java.util.concurrent.CompletableFuture

import io.github.hapjava.accessories.SwitchAccessory
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback

import scala.concurrent.{ExecutionContext, Future}

case class BeowulfeSwitch(id: Int, label: String)(implicit ec: ExecutionContext) extends SwitchAccessory with AccessoryConversions {

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

  override def getName = CompletableFuture.completedFuture(label)

  override def getId = id

  override def getManufacturer = CompletableFuture.completedFuture("Otah")

  override def getSerialNumber = CompletableFuture.completedFuture("none")

  override def getModel = CompletableFuture.completedFuture("none")

  override def getFirmwareRevision: CompletableFuture[String] = CompletableFuture.completedFuture(null)

  override def identify(): Unit = println("This is the original switch")
}
