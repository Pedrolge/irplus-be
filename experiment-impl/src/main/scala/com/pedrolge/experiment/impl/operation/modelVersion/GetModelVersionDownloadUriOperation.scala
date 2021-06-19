package com.pedrolge.experiment.impl.operation.modelVersion

import akka.NotUsed
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.modelVersion.Response.GetModelVersionDownloadUriResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class GetModelVersionDownloadUriOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[GetModelVersionDownloadUriOperation])

  def process(name: String, version: String)(implicit context: ExecutionContext): Future[GetModelVersionDownloadUriResponse] = {

    for {

      result <- mlflow
        .getModelVersionDownloadUri(name, version)
        .invoke(NotUsed)
        .map{result =>
          logger.info(s"getModelVersionDownloadUri = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}