package com.github.otah.hap.api.information

import com.github.otah.hap.api.*
import com.github.otah.hap.api.characteristics.*
import com.github.otah.hap.api.services.*
import spray.json.JsValue

import scala.concurrent.{ExecutionContext, Future}

trait AccessoryInformation extends SpecializedService {

  override final val serviceType = hap.service.accessoryInformation

  def identify: Required[IdentifyCharacteristic]
  def manufacturer: Required[InfoCharacteristic]
  def model: Required[InfoCharacteristic]
  def name: Required[NameCharacteristic]
  def serialNumber: Required[InfoCharacteristic]
  def firmwareRevision: Required[InfoCharacteristic]

  def hardwareRevision: Optional[InfoCharacteristic]
  def accessoryFlags: Optional[Characteristic] //TODO proper type

  override def all: AllSupported = AllSupported(
    name,
    identify,
    manufacturer,
    model,
    serialNumber,
    firmwareRevision,
    hardwareRevision,
    accessoryFlags,
  )
}

object AccessoryInformation {

  trait FromInfo extends AccessoryInformation with InfoProvider with IdStrategy.Automatic {

    override def identify = ??? //FIXME IdentifyCharacteristic(homeKitInfo.identification)

    override def manufacturer = InfoCharacteristic.manufacturer(homeKitInfo.manufacturer)

    override def model = InfoCharacteristic.model(homeKitInfo.model)

    override def name = ??? //FIXME NameCharacteristic(homeKitInfo.label) //TODO is label the correct info?

    override def serialNumber = InfoCharacteristic.serialNumber(homeKitInfo.serialNumber)

    override def firmwareRevision = ??? //FIXME RevisionCharacteristic.firmware(homeKitInfo.firmwareRevision)

    override def hardwareRevision = ??? //FIXME homeKitInfo.hardwareRevision map RevisionCharacteristic.hardware

    override def accessoryFlags = ??? //FIXME AccessoryFlagsCharacteristic.fixed(homeKitInfo.accessoryFlags) map (() => _)
  }

  /** Creates an Identified accessory info service from an info
    * @param info Info object to be translated to the respective info characteristics.
    * @param iid Instance ID of the accessory service. Defaults to 1
    * @return Identified service for accessory information
    */
  def fromInfo(info: HomeKitInfo): AccessoryInformation = {
    new FromInfo {
      override def iid: InstanceId = InstanceId(1)
      override def homeKitInfo: HomeKitInfo = info

      //FIXME stub of info

      override def characteristicsWrite(x: Map[InstanceId, JsValue])(implicit ec: ExecutionContext): Seq[Future[_]] = ???

      override def characteristicsValues()(implicit ec: ExecutionContext): Map[InstanceId, Future[JsValue]] = ???

      override def characteristicsValues(ids: Set[InstanceId])(implicit ec: ExecutionContext): Map[InstanceId, Future[JsValue]] = ???

      override def characteristicsSubscribe(callback: Map[InstanceId, JsValue] => Unit): Subscription = ???
    }
  }
}
