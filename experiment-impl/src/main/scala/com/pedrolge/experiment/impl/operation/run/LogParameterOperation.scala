package com.pedrolge.experiment.impl.operation.run

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.run.Request.{LogMetricRequest, LogParameterRequest}
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class LogParameterOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[LogParameterOperation])

  def process(logParameterRequest: LogParameterRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {

      result <- mlflow
        .logParameter()
        .invoke(logParameterRequest)
        .map{result =>
          logger.info(s"logParameter = ${result.toString}")
          Done
        }
    } yield {
      result
    }
  }

}