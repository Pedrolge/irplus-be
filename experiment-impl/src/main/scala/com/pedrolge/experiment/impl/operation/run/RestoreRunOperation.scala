package com.pedrolge.experiment.impl.operation.run

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.run.Request.RestoreRunRequest
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class RestoreRunOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[RestoreRunOperation])

  def process(restoreRunRequest: RestoreRunRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {

      result <- mlflow
        .restoreRun()
        .invoke(restoreRunRequest)
        .map{result =>
          logger.info(s"restoreRun = ${result.toString}")
          Done
        }
    } yield {
      result
    }
  }

}