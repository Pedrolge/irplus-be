package com.pedrolge.mlflow.api.model.experiment

import com.pedrolge.mlflow.api.model.{Experiment, MLFlowFormatter, RunInfo}
import play.api.libs.json.{Json, OFormat}

object Response {

  case class CreateExperimentResponse(
                                       experimentId: String
                                     )

  object CreateExperimentResponse extends MLFlowFormatter {
    implicit val format: OFormat[CreateExperimentResponse] = Json.format
  }

  case class ListExperimentResponse(
                                     experiments: List[Experiment]
                                   )

  object ListExperimentResponse extends MLFlowFormatter {
    implicit val format: OFormat[ListExperimentResponse] = Json.format
  }

  case class GetExperimentResponse(
                                    experiment: Experiment,
                                    runs: Option[List[RunInfo]]
                                  )

  object GetExperimentResponse extends MLFlowFormatter {
    implicit val format: OFormat[GetExperimentResponse] = Json.format
  }

  case class GetExperimentByNameResponse(
                                          experiment: Experiment
                                        )

  object GetExperimentByNameResponse extends MLFlowFormatter {
    implicit val format: OFormat[GetExperimentByNameResponse] = Json.format
  }

}
