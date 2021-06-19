package com.pedrolge.experiment.impl.operation.registeredModel

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.registeredModel.Request.SetRegisteredModelTagRequest
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class SetRegisteredModelTagOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[SetRegisteredModelTagOperation])

  def process(setRegisteredModelTagRequest: SetRegisteredModelTagRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {

      result <- mlflow
        .setRegisteredModelTag()
        .invoke(setRegisteredModelTagRequest)
        .map{result =>
          logger.info(s"setRegisteredModelTag = ${result.toString}")
          Done
        }
    } yield {
      result
    }
  }

}