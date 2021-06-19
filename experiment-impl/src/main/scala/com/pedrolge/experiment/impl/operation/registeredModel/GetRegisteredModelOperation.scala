package com.pedrolge.experiment.impl.operation.registeredModel

import akka.NotUsed
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.registeredModel.Response.GetRegisteredModelResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class GetRegisteredModelOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[GetRegisteredModelOperation])

  def process(name: String)(implicit context: ExecutionContext): Future[GetRegisteredModelResponse] = {

    for {

      result <- mlflow
        .getRegisteredModel(name)
        .invoke(NotUsed)
        .map{result =>
          logger.info(s"getRegisteredModel = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}