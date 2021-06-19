package com.pedrolge.experiment.impl.operation.run

import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.run.Request.CreateRunRequest
import com.pedrolge.mlflow.api.model.run.Response.CreateRunResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class CreateRunOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[CreateRunOperation])

  def process(createRunRequest: CreateRunRequest)(implicit context: ExecutionContext): Future[CreateRunResponse] = {

    for {

      result <- mlflow
        .createRun()
        .invoke(createRunRequest)
        .map{result =>
          logger.info(s"createRun = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}