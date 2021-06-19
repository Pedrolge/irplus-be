package com.pedrolge.mlflow.api.model.modelVersion

import com.pedrolge.mlflow.api.model.{MLFlowFormatter, ModelVersion}
import play.api.libs.json.{Json, OFormat}

object Response {

  case class CreateModelVersionResponse(
                                         modelVersion: ModelVersion
                                       )

  object CreateModelVersionResponse extends MLFlowFormatter {
    implicit val format: OFormat[CreateModelVersionResponse] = Json.format
  }

  case class GetModelVersionResponse(
                                      modelVersion: ModelVersion
                                    )

  object GetModelVersionResponse extends MLFlowFormatter {
    implicit val format: OFormat[GetModelVersionResponse] = Json.format
  }

  case class UpdateModelVersionResponse(
                                         modelVersion: ModelVersion
                                       )

  object UpdateModelVersionResponse extends MLFlowFormatter {
    implicit val format: OFormat[UpdateModelVersionResponse] = Json.format
  }

  case class SearchModelVersionResponse(
                                         modelVersions: List[ModelVersion]
                                       )

  object SearchModelVersionResponse extends MLFlowFormatter {
    implicit val format: OFormat[SearchModelVersionResponse] = Json.format
  }

  case class GetModelVersionDownloadUriResponse(
                                                 artifactUri: String
                                               )

  object GetModelVersionDownloadUriResponse extends MLFlowFormatter {
    implicit val format: OFormat[GetModelVersionDownloadUriResponse] = Json.format
  }

  case class TransitionModelVersionStageResponse(
                                                  modelVersion: ModelVersion
                                                )

  object TransitionModelVersionStageResponse extends MLFlowFormatter {
    implicit val format: OFormat[TransitionModelVersionStageResponse] = Json.format
  }


}
