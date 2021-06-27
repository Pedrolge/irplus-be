package com.pedrolge.experiment.impl.operation.registeredModel

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.registeredModel.Request.DeleteRegisteredModelRequest
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class DeleteRegisteredModelOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[DeleteRegisteredModelOperation])

  def process(deleteRegisteredModelRequest: DeleteRegisteredModelRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {

      result <- mlflow
        .deleteRegisteredModel()
        .invoke(deleteRegisteredModelRequest)
        .map{result =>
          logger.info(s"deleteRegisteredModel = ${result.toString}")
          Done
        }
    } yield {
      result
    }
  }

}