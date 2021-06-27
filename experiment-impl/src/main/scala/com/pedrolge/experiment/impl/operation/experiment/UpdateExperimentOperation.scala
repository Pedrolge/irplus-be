package com.pedrolge.experiment.impl.operation.experiment

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.experiment.Request.UpdateExperimentRequest
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class UpdateExperimentOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[UpdateExperimentOperation])

  def process(updateExperimentRequest: UpdateExperimentRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {

      result <- mlflow
        .updateExperiment()
        .invoke(updateExperimentRequest)
        .map{result =>
          logger.info(s"update = ${result.toString}")
          Done
        }


    } yield {
      result
    }
  }

}