package com.pedrolge.experiment.impl.operation.experiment

import akka.NotUsed
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.ViewType
import com.pedrolge.mlflow.api.model.experiment.Response.ListExperimentResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class ListExperimentsOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[ListExperimentsOperation])

  def process(viewType: ViewType.Value)(implicit context: ExecutionContext): Future[ListExperimentResponse] = {

    for {

      result <- mlflow
        .listExperiment(viewType)
        .invoke(NotUsed)
        .map{result =>
          logger.info(s"list = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}