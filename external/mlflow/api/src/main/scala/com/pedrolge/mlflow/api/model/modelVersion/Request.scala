package com.pedrolge.mlflow.api.model.modelVersion

import com.pedrolge.mlflow.api.model.{MLFlowFormatter, ModelVersionTag}
import play.api.libs.json.{Json, OFormat}

object Request {

  case class CreateModelVersionRequest(
                                        name: String,
                                        source: String,
                                        runId: String,
                                        tags: Option[List[ModelVersionTag]],
                                        runLink: Option[String],
                                        description: Option[String]
                                      )

  object CreateModelVersionRequest extends MLFlowFormatter {
    implicit val format: OFormat[CreateModelVersionRequest] = Json.format
  }

  case class UpdateModelVersionRequest(
                                        name: String,
                                        version: String,
                                        description: String
                                      )

  object UpdateModelVersionRequest extends MLFlowFormatter {
    implicit val format: OFormat[UpdateModelVersionRequest] = Json.format
  }

  case class DeleteModelVersionRequest(
                                        name: String,
                                        version: String,
                                      )

  object DeleteModelVersionRequest extends MLFlowFormatter {
    implicit val format: OFormat[DeleteModelVersionRequest] = Json.format
  }

  case class SearchModelVersionRequest(
                                        filter: String,
                                        maxResults: Int,
                                        orderBy: List[String],
                                        pageToken: Option[String]
                                      )

  object SearchModelVersionRequest extends MLFlowFormatter {
    implicit val format: OFormat[SearchModelVersionRequest] = Json.format
  }

  case class TransitionModelVersionStageRequest(
                                                 name: String,
                                                 version: String,
                                                 stage: String,
                                                 archiveExistingVersions: Boolean
                                               )

  object TransitionModelVersionStageRequest extends MLFlowFormatter {
    implicit val format: OFormat[TransitionModelVersionStageRequest] = Json.format
  }

  case class SetModelVersionTagRequest(
                                        name: String,
                                        version: String,
                                        key: String,
                                        value: String
                                      )

  object SetModelVersionTagRequest extends MLFlowFormatter {
    implicit val format: OFormat[SetModelVersionTagRequest] = Json.format
  }

  case class DeleteModelVersionTagRequest(
                                           name: String,
                                           version: String,
                                           key: String,
                                         )

  object DeleteModelVersionTagRequest extends MLFlowFormatter {
    implicit val format: OFormat[DeleteModelVersionTagRequest] = Json.format
  }


}
