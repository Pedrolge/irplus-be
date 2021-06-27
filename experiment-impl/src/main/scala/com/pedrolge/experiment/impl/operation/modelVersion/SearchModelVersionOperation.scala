package com.pedrolge.experiment.impl.operation.modelVersion

import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.modelVersion.Request.SearchModelVersionRequest
import com.pedrolge.mlflow.api.model.modelVersion.Response.SearchModelVersionResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class SearchModelVersionOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[SearchModelVersionOperation])

  def process(searchModelVersionRequest: SearchModelVersionRequest)(implicit context: ExecutionContext): Future[SearchModelVersionResponse] = {

    for {

      result <- mlflow
        .searchModelVersion()
        .invoke(searchModelVersionRequest)
        .map{result =>
          logger.info(s"searchModelVersion = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}