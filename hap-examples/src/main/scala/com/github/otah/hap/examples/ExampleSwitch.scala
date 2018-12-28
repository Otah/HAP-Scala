package com.github.otah.hap.examples

import com.github.otah.hap.api.characteristics.PowerStateCharacteristic
import com.github.otah.hap.api.services.{SequenceInstanceIds, SwitchService}
import com.github.otah.hap.observable.ObservableWritableCharacteristic
import monix.reactive.subjects.BehaviorSubject

case class ExampleSwitch(id: Int, label: String)
                        (subject: BehaviorSubject[Boolean])
        extends BaseAccessory with SwitchService with SequenceInstanceIds {

  override val powerState: PowerStateCharacteristic =
    new ObservableWritableCharacteristic(subject) with PowerStateCharacteristic
}
