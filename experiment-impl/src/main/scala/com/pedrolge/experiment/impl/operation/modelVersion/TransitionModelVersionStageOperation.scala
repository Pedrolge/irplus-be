package com.pedrolge.experiment.impl.operation.modelVersion

import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.modelVersion.Request.TransitionModelVersionStageRequest
import com.pedrolge.mlflow.api.model.modelVersion.Response.TransitionModelVersionStageResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class TransitionModelVersionStageOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[TransitionModelVersionStageOperation])

  def process(transitionModelVersionStageRequest: TransitionModelVersionStageRequest)(implicit context: ExecutionContext): Future[TransitionModelVersionStageResponse] = {

    for {

      result <- mlflow
        .transitionModelVersionStage()
        .invoke(transitionModelVersionStageRequest)
        .map{result =>
          logger.info(s"transitionModelVersionStage = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}