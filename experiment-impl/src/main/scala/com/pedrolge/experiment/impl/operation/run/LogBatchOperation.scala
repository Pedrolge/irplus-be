package com.pedrolge.experiment.impl.operation.run

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.run.Request.LogBatchRequest
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class LogBatchOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[LogBatchOperation])

  def process(logBatchRequest: LogBatchRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {

      result <- mlflow
        .logBatch()
        .invoke(logBatchRequest)
        .map{result =>
          logger.info(s"logBatch = ${result.toString}")
          Done
        }
    } yield {
      result
    }
  }

}