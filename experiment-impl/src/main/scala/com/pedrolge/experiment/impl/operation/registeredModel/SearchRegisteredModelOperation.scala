package com.pedrolge.experiment.impl.operation.registeredModel

import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.registeredModel.Request.SearchRegisteredModelRequest
import com.pedrolge.mlflow.api.model.registeredModel.Response.SearchRegisteredModelResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class SearchRegisteredModelOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[SearchRegisteredModelOperation])

  def process(searchRegisteredModelRequest: SearchRegisteredModelRequest)(implicit context: ExecutionContext): Future[SearchRegisteredModelResponse] = {

    for {

      result <- mlflow
        .searchRegisteredModel()
        .invoke(searchRegisteredModelRequest)
        .map{result =>
          logger.info(s"searchRegisteredModel = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}