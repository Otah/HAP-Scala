package com.github.otah
package hap.examples

import hap.api._
import characteristics.PowerStateCharacteristic
import services._
import hap.monix._
import monix.execution.Scheduler
import monix.reactive.subjects.BehaviorSubject
import spray.json.JsValue

import scala.concurrent.{ExecutionContext, Future}

class ExampleSwitch(val label: String, subject: BehaviorSubject[Boolean])
                   (implicit s: Scheduler)
  extends BaseAccessory with SwitchService with FilterSubsetFromAll with ObservableService {

  override val powerState: Required[PowerStateCharacteristic] = new A with PowerStateCharacteristic

  override def characteristicsWrite(updates: Update)(implicit ec: ExecutionContext): Seq[Future[_]] = xxx

  override def characteristicsValues()(implicit ec: ExecutionContext): Map[InstanceId, Future[JsValue]] = Map(
    powerState withValue subject.firstL.runToFuture,
  )

  override protected val notifier = ObservableNotifier(subject map (power => Map(powerState withValue power)))
}
