package com.pedrolge.experiment.impl.operation.modelVersion

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.modelVersion.Request.SetModelVersionTagRequest
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class SetModelVersionTagOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[SetModelVersionTagOperation])

  def process(setModelVersionTagRequest: SetModelVersionTagRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {

      result <- mlflow
        .setModelVersionTag()
        .invoke(setModelVersionTagRequest)
        .map{result =>
          logger.info(s"setModelVersionTag = ${result.toString}")
          Done
        }
    } yield {
      result
    }
  }

}