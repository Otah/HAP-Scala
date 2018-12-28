package com.github.otah.hap.api.internal

import scalajson.ast.JValue

import scala.concurrent.Future

trait LowLevelNotifier {
  def subscribe(callback: JValue => Future[Unit]): Subscription
}
