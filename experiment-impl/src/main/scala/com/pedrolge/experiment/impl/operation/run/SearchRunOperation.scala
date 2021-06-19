package com.pedrolge.experiment.impl.operation.run

import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.run.Request.SearchRunRequest
import com.pedrolge.mlflow.api.model.run.Response.SearchRunResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class SearchRunOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[SearchRunOperation])

  def process(searchRunRequest: SearchRunRequest)(implicit context: ExecutionContext): Future[SearchRunResponse] = {

    for {

      result <- mlflow
        .searchRun()
        .invoke(searchRunRequest)
        .map{result =>
          logger.info(s"searchRun = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}