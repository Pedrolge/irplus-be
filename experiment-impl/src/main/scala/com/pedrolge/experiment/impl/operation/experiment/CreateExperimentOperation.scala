package com.pedrolge.experiment.impl.operation.experiment

import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.experiment.Request.CreateExperimentRequest
import com.pedrolge.mlflow.api.model.experiment.Response.CreateExperimentResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class CreateExperimentOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[CreateExperimentOperation])

  def process(createExperimentRequest: CreateExperimentRequest)(implicit context: ExecutionContext): Future[CreateExperimentResponse] = {

    for {

      result <- mlflow
        .createExperiment()
        .invoke(createExperimentRequest)
        .map{result =>
          logger.info(s"create = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}