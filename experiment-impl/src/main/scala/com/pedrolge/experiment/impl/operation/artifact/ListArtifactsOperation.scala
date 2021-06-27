package com.pedrolge.experiment.impl.operation.artifact

import akka.NotUsed
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.artifact.Response.ListArtifactsResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class ListArtifactsOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[ListArtifactsOperation])

  def process(runId: String, path: Option[String], pageToken: Option[String])(implicit context: ExecutionContext): Future[ListArtifactsResponse] = {

    for {
      result <- mlflow
        .listArtifacts(runId, path, pageToken)
        .invoke(NotUsed)
        .map{result =>
          logger.info(s"listArtifacts = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}