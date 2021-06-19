package com.pedrolge.experiment.impl.operation.registeredModel

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.registeredModel.Request.DeleteRegisteredModelTagRequest
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class DeleteRegisteredModelTagOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[DeleteRegisteredModelTagOperation])

  def process(deleteRegisteredModelTagRequest: DeleteRegisteredModelTagRequest)(implicit context: ExecutionContext): Future[Done
  ] = {

    for {

      result <- mlflow
        .deleteRegisteredModelTag()
        .invoke(deleteRegisteredModelTagRequest)
        .map{result =>
          logger.info(s"deleteRegisteredModelTag = ${result.toString}")
          Done
        }
    } yield {
      result
    }
  }

}