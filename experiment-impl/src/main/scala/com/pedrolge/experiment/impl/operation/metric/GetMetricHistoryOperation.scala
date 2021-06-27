package com.pedrolge.experiment.impl.operation.metric

import akka.NotUsed
import com.pedrolge.mlflow.api.MLFlow
import com.pedrolge.mlflow.api.model.metric.Response.GetMetricHistoryResponse
import com.pedrolge.mlflow.api.model.run.Response.GetRunResponse
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class GetMetricHistoryOperation(mlflow: MLFlow) {

  val logger: Logger = Logger(classOf[GetMetricHistoryOperation])

  def process(runId: String, metricKey: String)(implicit context: ExecutionContext): Future[GetMetricHistoryResponse] = {

    for {

      result <- mlflow
        .getMetricHistory(runId, metricKey)
        .invoke(NotUsed)
        .map{result =>
          logger.info(s"getMetricHistory = ${result.toString}")
          result
        }
    } yield {
      result
    }
  }

}