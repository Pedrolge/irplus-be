package com.pedrolge.experiment.impl.operation.modelVersion

import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.modelVersion.Request.UpdateModelVersionRequest
import com.pedrolge.mlflow.api.model.modelVersion.Response.UpdateModelVersionResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class UpdateModelVersionOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[UpdateModelVersionOperation])

  def process(updateModelVersionRequest: UpdateModelVersionRequest)(implicit context: ExecutionContext): Future[UpdateModelVersionResponse] = {

    for {

      result <- mlflow
        .updateModelVersion()
        .invoke(updateModelVersionRequest)
        .map{result =>
          logger.info(s"updateModelVersion = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}