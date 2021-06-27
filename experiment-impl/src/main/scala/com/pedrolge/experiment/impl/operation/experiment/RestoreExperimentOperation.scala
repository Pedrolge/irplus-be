package com.pedrolge.experiment.impl.operation.experiment

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.experiment.Request.RestoreExperimentRequest
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class RestoreExperimentOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[RestoreExperimentOperation])

  def process(restoreExperimentRequest: RestoreExperimentRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {
      result <- mlflow
        .restoreExperiment()
        .invoke(restoreExperimentRequest)
        .map{result =>
          logger.info(s"restore = ${result.toString}")
          Done
        }
    } yield {
      result
    }
  }

}