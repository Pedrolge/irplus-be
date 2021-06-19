package com.pedrolge.experiment.impl.operation.run

import akka.Done
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.run.Request.DeleteTagRequest
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class DeleteTagOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[DeleteTagOperation])

  def process(deleteTagRequest: DeleteTagRequest)(implicit context: ExecutionContext): Future[Done] = {

    for {

      result <- mlflow
        .deleteTag()
        .invoke(deleteTagRequest)
        .map{result =>
          logger.info(s"deleteTag = ${result.toString}")
          Done
        }
    } yield {
      result
    }
  }

}