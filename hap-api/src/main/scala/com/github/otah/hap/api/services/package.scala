package com.github.otah.hap.api

import scala.language.implicitConversions

package object services {

  implicit def characteristicInstanceAutoOption(x: Identified[LowLevelCharacteristic]) = Some(x)

  type Characteristics = Seq[Identified[LowLevelCharacteristic]]

  def Characteristics(chs: Option[Identified[LowLevelCharacteristic]]*): Characteristics = chs.flatten

  trait Has1Characteristic {
    def id1: InstanceId
  }

  trait Has2Characteristics extends Has1Characteristic {
    def id2: InstanceId
  }

  trait Has3Characteristics extends Has2Characteristics {
    def id3: InstanceId
  }

  trait Has4Characteristics extends Has3Characteristics {
    def id4: InstanceId
  }

  trait Has5Characteristics extends Has4Characteristics {
    def id5: InstanceId
  }

  trait Has6Characteristics extends Has5Characteristics {
    def id6: InstanceId
  }

  trait SequenceInstanceIds extends Has6Characteristics { // TODO use the largest one

    def baseInstanceId: Int

    def id(i: Int) = InstanceId(baseInstanceId + i)

    override def id1: InstanceId = id(1)
    override def id2: InstanceId = id(2)
    override def id3: InstanceId = id(3)
    override def id4: InstanceId = id(4)
    override def id5: InstanceId = id(5)
    override def id6: InstanceId = id(6)
  }

  abstract class ServiceWithSequentialIds(val baseInstanceId: Int) extends AccessoryService with SequenceInstanceIds
}
