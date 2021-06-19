package com.pedrolge.mlflow.api.model.artifact

import com.pedrolge.mlflow.api.model.{FileInfo, MLFlowFormatter}
import play.api.libs.json.{Json, OFormat}

object Response {

  case class ListArtifactsResponse(
                                       rootUri: String,
                                       files: List[FileInfo],
                                       nextPageToken: Option[String]
                                     )

  object ListArtifactsResponse extends MLFlowFormatter {
    implicit val format: OFormat[ListArtifactsResponse] = Json.format
  }

}
