package com.pedrolge.experiment.impl.operation.modelVersion

import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.modelVersion.Request.CreateModelVersionRequest
import com.pedrolge.mlflow.api.model.modelVersion.Response.CreateModelVersionResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class CreateModelVersionOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[CreateModelVersionOperation])

  def process(createModelVersionRequest: CreateModelVersionRequest)(implicit context: ExecutionContext): Future[CreateModelVersionResponse] = {

    for {

      result <- mlflow
        .createModelVersion()
        .invoke(createModelVersionRequest)
        .map{result =>
          logger.info(s"createModelVersion = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}