package com.pedrolge.experiment.impl.operation.experiment

import akka.NotUsed
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.experiment.Response.GetExperimentByNameResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class GetExperimentByNameOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[GetExperimentByNameOperation])

  def process(name: String)(implicit context: ExecutionContext): Future[GetExperimentByNameResponse] = {

    for {
      result <- mlflow
      .getExperimentByName(name)
      .invoke(NotUsed)
      .map{result =>
        logger.info(s"getByName = ${result.toString}")
        result
      }

    } yield {
      result
    }
  }

}