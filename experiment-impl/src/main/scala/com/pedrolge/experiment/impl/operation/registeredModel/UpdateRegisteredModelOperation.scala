package com.pedrolge.experiment.impl.operation.registeredModel

import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.registeredModel.Request.UpdateRegisteredModelRequest
import com.pedrolge.mlflow.api.model.registeredModel.Response.UpdateRegisteredModelResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class UpdateRegisteredModelOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[UpdateRegisteredModelOperation])

  def process(updateRegisteredModelRequest: UpdateRegisteredModelRequest)(implicit context: ExecutionContext): Future[UpdateRegisteredModelResponse] = {

    for {

      result <- mlflow
        .updateRegisteredModel()
        .invoke(updateRegisteredModelRequest)
        .map{result =>
          logger.info(s"updateRegisteredModel = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}