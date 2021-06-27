package com.pedrolge.mlflow.api.model.registeredModel

import com.pedrolge.mlflow.api.model.{MLFlowFormatter, RegisteredModelTag}
import play.api.libs.json.{Json, OFormat}

object Request {

  case class CreateRegisteredModelRequest(
                                           name: String,
                                           tags: Option[List[RegisteredModelTag]],
                                           description: Option[String]
                                         )

  object CreateRegisteredModelRequest extends MLFlowFormatter {
    implicit val format: OFormat[CreateRegisteredModelRequest] = Json.format
  }

  case class RenameRegisteredModelRequest(
                                           name: String,
                                           newName: String
                                         )

  object RenameRegisteredModelRequest extends MLFlowFormatter {
    implicit val format: OFormat[RenameRegisteredModelRequest] = Json.format
  }

  case class UpdateRegisteredModelRequest(
                                           name: String,
                                           description: String
                                         )

  object UpdateRegisteredModelRequest extends MLFlowFormatter {
    implicit val format: OFormat[UpdateRegisteredModelRequest] = Json.format
  }

  case class DeleteRegisteredModelRequest(
                                           name: String
                                         )

  object DeleteRegisteredModelRequest extends MLFlowFormatter {
    implicit val format: OFormat[DeleteRegisteredModelRequest] = Json.format
  }

  case class SearchRegisteredModelRequest(
                                           filter: String,
                                           maxResults: Int,
                                           orderBy: List[String],
                                           pageToken: Option[String]
                                         )

  object SearchRegisteredModelRequest extends MLFlowFormatter {
    implicit val format: OFormat[SearchRegisteredModelRequest] = Json.format
  }

  case class SetRegisteredModelTagRequest(
                                           name: String,
                                           key: String,
                                           value: String
                                         )

  object SetRegisteredModelTagRequest extends MLFlowFormatter {
    implicit val format: OFormat[SetRegisteredModelTagRequest] = Json.format
  }

  case class DeleteRegisteredModelTagRequest(
                                              name: String,
                                              key: String
                                            )

  object DeleteRegisteredModelTagRequest extends MLFlowFormatter {
    implicit val format: OFormat[DeleteRegisteredModelTagRequest] = Json.format
  }

}
