package com.pedrolge.experiment.impl.operation.experiment

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.experiment.Request.DeleteExperimentRequest
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class DeleteExperimentOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[DeleteExperimentOperation])

  def process(deleteExperimentRequest: DeleteExperimentRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {

      result <- mlflow
        .deleteExperiment()
        .invoke(deleteExperimentRequest)
        .map{result =>
          logger.info(s"delete = ${result.toString}")
          Done
        }
    } yield {
      result
    }
  }

}