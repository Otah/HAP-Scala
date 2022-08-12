package com.github.otah.hap.api

import com.github.otah.hap.api.services.Characteristics

class InstanceIdConversionsCompileCheck {

  case class Ids(id: InstanceId.Ops)

  def apply() = {

    val id1 = Ids(1)
    val id2 = Ids(InstanceId(1))

    def bigFun(iid: InstanceId): Unit = ()
    bigFun(id1.id)

    (id1, id2)
  }
}
