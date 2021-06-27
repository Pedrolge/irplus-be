package com.pedrolge.experiment.impl.operation.run

import akka.{Done, NotUsed}
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.run.Response.GetRunResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class GetRunOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[GetRunOperation])

  def process(runId: String)(implicit context: ExecutionContext): Future[GetRunResponse] = {

    for {

      result <- mlflow
        .getRun(runId)
        .invoke(NotUsed)
        .map{result =>
          logger.info(s"getRun = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}