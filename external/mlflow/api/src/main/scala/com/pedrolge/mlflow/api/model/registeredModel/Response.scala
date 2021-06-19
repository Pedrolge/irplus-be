package com.pedrolge.mlflow.api.model.registeredModel

import com.pedrolge.mlflow.api.model.{MLFlowFormatter, Metric, ModelVersion, RegisteredModel}
import play.api.libs.json.{Json, OFormat}

object Response {

  case class CreateRegisteredModelResponse(
                                            registeredModel: RegisteredModel
                                          )

  object CreateRegisteredModelResponse extends MLFlowFormatter {
    implicit val format: OFormat[CreateRegisteredModelResponse] = Json.format
  }

  case class GetRegisteredModelResponse(
                                         registeredModel: RegisteredModel
                                       )

  object GetRegisteredModelResponse extends MLFlowFormatter {
    implicit val format: OFormat[GetRegisteredModelResponse] = Json.format
  }

  case class RenameRegisteredModelResponse(
                                            registeredModel: RegisteredModel
                                          )

  object RenameRegisteredModelResponse extends MLFlowFormatter {
    implicit val format: OFormat[RenameRegisteredModelResponse] = Json.format
  }

  case class UpdateRegisteredModelResponse(
                                            registeredModel: RegisteredModel
                                          )

  object UpdateRegisteredModelResponse extends MLFlowFormatter {
    implicit val format: OFormat[UpdateRegisteredModelResponse] = Json.format
  }

  case class ListRegisteredModelResponse(
                                          registeredModel: RegisteredModel,
                                          nextPageToken: Option[String]
                                        )

  object ListRegisteredModelResponse extends MLFlowFormatter {
    implicit val format: OFormat[ListRegisteredModelResponse] = Json.format
  }

  case class GetLatestVersionsResponse(
                                        modelVersions: List[ModelVersion]
                                      )

  object GetLatestVersionsResponse extends MLFlowFormatter {
    implicit val format: OFormat[GetLatestVersionsResponse] = Json.format
  }

  case class SearchRegisteredModelResponse(
                                            registeredModels: List[RegisteredModel],
                                            nextPageToken: Option[String]
                                          )

  object SearchRegisteredModelResponse extends MLFlowFormatter {
    implicit val format: OFormat[SearchRegisteredModelResponse] = Json.format
  }


}
