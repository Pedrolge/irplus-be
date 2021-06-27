package com.pedrolge.experiment.impl.operation.registeredModel

import akka.NotUsed
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.registeredModel.Response.ListRegisteredModelResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class ListRegisteredModelOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[ListRegisteredModelOperation])

  def process(maxResults: Int, pageToken: Option[String])(implicit context: ExecutionContext): Future[ListRegisteredModelResponse] = {

    for {

      result <- mlflow
        .listRegisteredModel(maxResults, pageToken)
        .invoke(NotUsed)
        .map{result =>
          logger.info(s"listRegisteredModel = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}