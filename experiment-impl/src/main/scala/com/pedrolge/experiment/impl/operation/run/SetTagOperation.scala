package com.pedrolge.experiment.impl.operation.run

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.run.Request.SetTagRequest
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class SetTagOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[SetTagOperation])

  def process(setTagRequest: SetTagRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {

      result <- mlflow
        .setTag()
        .invoke(setTagRequest)
        .map{result =>
          logger.info(s"setTag = ${result.toString}")
          Done
        }
    } yield {
      result
    }
  }

}