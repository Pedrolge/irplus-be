package com.pedrolge.experiment.impl.operation.modelVersion

import akka.NotUsed
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.modelVersion.Request.CreateModelVersionRequest
import com.pedrolge.mlflow.api.model.modelVersion.Response.{CreateModelVersionResponse, GetModelVersionResponse}
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class GetModelVersionOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[GetModelVersionOperation])

  def process(name: String, version: String)(implicit context: ExecutionContext): Future[GetModelVersionResponse] = {

    for {

      result <- mlflow
        .getModelVersion(name, version)
        .invoke(NotUsed)
        .map{result =>
          logger.info(s"getModelVersion = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}