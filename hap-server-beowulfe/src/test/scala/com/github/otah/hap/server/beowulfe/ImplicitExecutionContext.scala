package com.github.otah.hap.server.beowulfe

import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext

trait ImplicitExecutionContext {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(Executors.newCachedThreadPool())
}
