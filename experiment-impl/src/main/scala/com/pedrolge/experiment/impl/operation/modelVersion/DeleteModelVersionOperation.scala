package com.pedrolge.experiment.impl.operation.modelVersion

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.modelVersion.Request.DeleteModelVersionRequest
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class DeleteModelVersionOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[DeleteModelVersionOperation])

  def process(deleteModelVersionRequest: DeleteModelVersionRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {

      result <- mlflow
        .deleteModelVersion()
        .invoke(deleteModelVersionRequest)
        .map{result =>
          logger.info(s"deleteModelVersion = ${result.toString}")
          Done
        }
    } yield {
      result
    }
  }

}