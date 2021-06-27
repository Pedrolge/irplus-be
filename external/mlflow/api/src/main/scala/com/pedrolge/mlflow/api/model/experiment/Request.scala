package com.pedrolge.mlflow.api.model.experiment

import com.pedrolge.mlflow.api.model.MLFlowFormatter
import play.api.libs.json.{Json, OFormat}

object Request {

  case class CreateExperimentRequest(
                                      name: String,
                                      artifactLocation: Option[String]
                                    )

  object CreateExperimentRequest extends MLFlowFormatter {
    implicit val format: OFormat[CreateExperimentRequest] = Json.format
  }

  case class DeleteExperimentRequest(
                                      experimentId: String
                                    )

  object DeleteExperimentRequest extends MLFlowFormatter {
    implicit val format: OFormat[DeleteExperimentRequest] = Json.format
  }

  case class RestoreExperimentRequest(
                                       experimentId: String
                                     )

  object RestoreExperimentRequest extends MLFlowFormatter {
    implicit val format: OFormat[RestoreExperimentRequest] = Json.format
  }

  case class UpdateExperimentRequest(
                                      experimentId: String,
                                      newName: String
                                    )

  object UpdateExperimentRequest extends MLFlowFormatter {
    implicit val format: OFormat[UpdateExperimentRequest] = Json.format
  }

  case class SetExperimentTagRequest(
                                      experimentId: String,
                                      key: String,
                                      value: String
                                    )

  object SetExperimentTagRequest extends MLFlowFormatter {
    implicit val format: OFormat[SetExperimentTagRequest] = Json.format
  }


}
