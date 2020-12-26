package com.github.otah.hap.examples

import com.github.otah.hap.api.characteristics.PowerStateCharacteristic
import com.github.otah.hap.api.services._
import com.github.otah.hap.monix.ObservableWritableCharacteristic
import monix.execution.Scheduler
import monix.reactive.subjects.BehaviorSubject

class ExampleSwitch(val label: String, subject: BehaviorSubject[Boolean])
                   (implicit s: Scheduler)
  extends BaseAccessory with SwitchService with IdStrategy.Automatic {

  override val powerState: Required[PowerStateCharacteristic] =
    new ObservableWritableCharacteristic(subject) with PowerStateCharacteristic
}
