package com.pedrolge.experiment.impl.operation.run

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.run.Request.{LogMetricRequest, RestoreRunRequest}
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class LogMetricOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[LogMetricOperation])

  def process(logMetricRequest: LogMetricRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {

      result <- mlflow
        .logMetric()
        .invoke(logMetricRequest)
        .map{result =>
          logger.info(s"logMetric = ${result.toString}")
          Done
        }
    } yield {
      result
    }
  }

}