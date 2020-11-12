package com.github.otah.hap.api.services

import com.github.otah.hap.api._

import scala.language.implicitConversions

package object experimental {

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

  trait Has7Characteristics extends Has6Characteristics {
    def id7: InstanceId
  }

  trait Has8Characteristics extends Has7Characteristics {
    def id8: InstanceId
  }

  trait SequenceInstanceIds extends Has8Characteristics { // TODO use the largest one

    def baseInstanceId: Int

    def id(i: Int) = InstanceId(baseInstanceId + i)

    override def id1: InstanceId = id(1)
    override def id2: InstanceId = id(2)
    override def id3: InstanceId = id(3)
    override def id4: InstanceId = id(4)
    override def id5: InstanceId = id(5)
    override def id6: InstanceId = id(6)
    override def id7: InstanceId = id(7)
    override def id8: InstanceId = id(8)
  }

  abstract class ServiceWithSequentialIds(val baseInstanceId: Int) extends AccessoryService with SequenceInstanceIds
}
