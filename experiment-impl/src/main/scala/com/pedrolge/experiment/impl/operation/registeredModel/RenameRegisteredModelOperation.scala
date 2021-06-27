package com.pedrolge.experiment.impl.operation.registeredModel

import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.registeredModel.Request.RenameRegisteredModelRequest
import com.pedrolge.mlflow.api.model.registeredModel.Response.RenameRegisteredModelResponse
import com.pedrolge.mlflow.api.model.run.Request.CreateRunRequest
import com.pedrolge.mlflow.api.model.run.Response.CreateRunResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class RenameRegisteredModelOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[RenameRegisteredModelOperation])

  def process(renameRegisteredModelRequest: RenameRegisteredModelRequest)(implicit context: ExecutionContext): Future[RenameRegisteredModelResponse] = {

    for {

      result <- mlflow
        .renameRegisteredModel()
        .invoke(renameRegisteredModelRequest)
        .map{result =>
          logger.info(s"renameRegisteredModel = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}