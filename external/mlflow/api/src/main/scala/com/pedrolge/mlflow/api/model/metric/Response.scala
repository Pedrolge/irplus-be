package com.pedrolge.mlflow.api.model.metric

import com.pedrolge.mlflow.api.model.{MLFlowFormatter, Metric}
import play.api.libs.json.{Json, OFormat}

object Response {

  case class GetMetricHistoryResponse(
                                       metrics: List[Metric]
                                     )

  object GetMetricHistoryResponse extends MLFlowFormatter {
    implicit val format: OFormat[GetMetricHistoryResponse] = Json.format
  }


}
