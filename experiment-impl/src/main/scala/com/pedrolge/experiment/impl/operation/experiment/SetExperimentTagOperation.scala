package com.pedrolge.experiment.impl.operation.experiment

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.experiment.Request.{SetExperimentTagRequest, UpdateExperimentRequest}
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class SetExperimentTagOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[SetExperimentTagOperation])

  def process(setExperimentTagRequest: SetExperimentTagRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {

      result <- mlflow
        .setExperimentTag()
        .invoke(setExperimentTagRequest)
        .map{result =>
          logger.info(s"setExperimentTag = ${result.toString}")
          Done
        }


    } yield {
      result
    }
  }

}