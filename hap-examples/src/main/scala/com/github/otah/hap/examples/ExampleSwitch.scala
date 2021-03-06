package com.github.otah.hap.examples

import com.github.otah.hap.api.characteristics.PowerStateCharacteristic
import com.github.otah.hap.api.services.SwitchService
import com.github.otah.hap.monix.ObservableWritableCharacteristic
import monix.execution.Scheduler
import monix.reactive.subjects.BehaviorSubject

class ExampleSwitch(val id: Int, val label: String, subject: BehaviorSubject[Boolean])
                   (implicit s: Scheduler)
  extends BaseAccessory with SwitchService {

  override val powerState: PowerStateCharacteristic =
    new ObservableWritableCharacteristic(subject) with PowerStateCharacteristic
}
