package com.pedrolge.experiment.impl.operation.experiment

import akka.NotUsed
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.experiment.Response.GetExperimentResponse
import com.typesafe.scalalogging.{LazyLogging, Logger}

import scala.concurrent.{ExecutionContext, Future}

class GetExperimentOperation(mlflow: MLFlow) extends LazyLogging {

//  val logger: Logger = Logger(classOf[GetExperimentOperation])

  def process(experimentId: String)(implicit context: ExecutionContext): Future[GetExperimentResponse] = {

    for {

      result <- mlflow
        .getExperiment(experimentId)
        .invoke(NotUsed)
        .map{result =>
          logger.info(s"get = ${result.toString}")
          result
        }

    } yield {
      result
    }
  }

}