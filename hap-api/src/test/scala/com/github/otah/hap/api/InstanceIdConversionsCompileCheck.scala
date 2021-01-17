package com.github.otah.hap.api

import com.github.otah.hap.api.services.Characteristics

class InstanceIdConversionsCompileCheck {

  case class Ids(id: InstanceId.Ops)

  def apply() = {

    val id1 = Ids(1)
    val id2 = Ids(InstanceId(1))

    def bigFun(iid: InstanceId): Unit = ()
    bigFun(id1.id)

    val svc1 = 1 --> new AccessoryService {
      override def serviceType: HapType = ???
      override def characteristics: Characteristics = ???
    }

    val svc2 = InstanceId(1) --> new AccessoryService {
      override def serviceType: HapType = ???
      override def characteristics: Characteristics = ???
    }

    (id1, id2, svc1, svc2)
  }
}
