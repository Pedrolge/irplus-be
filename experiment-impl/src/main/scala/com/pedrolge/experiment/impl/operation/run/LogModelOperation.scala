package com.pedrolge.experiment.impl.operation.run

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.run.Request.LogModelRequest
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class LogModelOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[LogModelOperation])

  def process(logModelRequest: LogModelRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {

      result <- mlflow
        .logModel()
        .invoke(logModelRequest)
        .map{result =>
          logger.info(s"logModel = ${result.toString}")
          Done
        }
    } yield {
      result
    }
  }

}