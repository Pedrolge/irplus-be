package com.pedrolge.experiment.impl.operation.registeredModel

import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.registeredModel.Request.CreateRegisteredModelRequest
import com.pedrolge.mlflow.api.model.registeredModel.Response.CreateRegisteredModelResponse
import com.pedrolge.mlflow.api.model.run.Request.CreateRunRequest
import com.pedrolge.mlflow.api.model.run.Response.CreateRunResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class CreateRegisteredModelOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[CreateRegisteredModelOperation])

  def process(createRegisteredModelRequest: CreateRegisteredModelRequest)(implicit context: ExecutionContext): Future[CreateRegisteredModelResponse] = {

    for {

      result <- mlflow
        .createRegisteredModel()
        .invoke(createRegisteredModelRequest)
        .map{result =>
          logger.info(s"createRegisteredModel = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}