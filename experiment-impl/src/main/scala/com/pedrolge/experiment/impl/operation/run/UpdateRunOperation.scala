package com.pedrolge.experiment.impl.operation.run

import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.run.Request.{SearchRunRequest, UpdateRunRequest}
import com.pedrolge.mlflow.api.model.run.Response.{SearchRunResponse, UpdateRunResponse}
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class UpdateRunOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[UpdateRunOperation])

  def process(updateRunRequest: UpdateRunRequest)(implicit context: ExecutionContext): Future[UpdateRunResponse] = {

    for {

      result <- mlflow
        .updateRun()
        .invoke(updateRunRequest)
        .map{result =>
          logger.info(s"updateRun = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}