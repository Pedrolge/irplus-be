package com.pedrolge.experiment.impl.operation.modelVersion

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.modelVersion.Request.DeleteModelVersionTagRequest
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class DeleteModelVersionTagOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[DeleteModelVersionTagOperation])

  def process(deleteModelVersionTagRequest: DeleteModelVersionTagRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {

      result <- mlflow
        .deleteModelVersionTag()
        .invoke(deleteModelVersionTagRequest)
        .map{result =>
          logger.info(s"deleteModelVersionTag = ${result.toString}")
          Done
        }
    } yield {
      result
    }
  }

}