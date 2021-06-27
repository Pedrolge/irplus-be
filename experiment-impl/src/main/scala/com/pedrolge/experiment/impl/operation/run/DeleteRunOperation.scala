package com.pedrolge.experiment.impl.operation.run

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.run.Request.{CreateRunRequest, DeleteRunRequest}
import com.pedrolge.mlflow.api.model.run.Response.CreateRunResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class DeleteRunOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[DeleteRunOperation])

  def process(deleteRunRequest: DeleteRunRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {

      result <- mlflow
        .deleteRun()
        .invoke(deleteRunRequest)
        .map{result =>
          logger.info(s"deleteRun = ${result.toString}")
          Done
        }
    } yield {
      result
    }
  }

}