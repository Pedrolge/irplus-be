package com.pedrolge.experiment.impl.operation.registeredModel

import akka.NotUsed
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.registeredModel.Response.GetLatestVersionsResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class GetLatestVersionsOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[GetLatestVersionsOperation])

  def process(name: String, stages: List[String])(implicit context: ExecutionContext): Future[GetLatestVersionsResponse] = {

    for {

      result <- mlflow
        .getLatestVersions(name, stages)
        .invoke(NotUsed)
        .map{result =>
          logger.info(s"getLatestVersions = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}