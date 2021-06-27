package com.pedrolge.mlflow.api.model.run

import com.pedrolge.mlflow.api.model.{MLFlowFormatter, Run, RunInfo}
import play.api.libs.json.{Json, OFormat}

object Response {

  case class CreateRunResponse(
                                run: Run
                              )

  object CreateRunResponse extends MLFlowFormatter {
    implicit val format: OFormat[CreateRunResponse] = Json.format
  }

  case class GetRunResponse(
                                run: Run
                              )

  object GetRunResponse extends MLFlowFormatter {
    implicit val format: OFormat[GetRunResponse] = Json.format
  }

  case class SearchRunResponse(
                                runs: Option[List[Run]],
                                nextPageToken: Option[String]
                              )

  object SearchRunResponse extends MLFlowFormatter {
    implicit val format: OFormat[SearchRunResponse] = Json.format
  }

  case class UpdateRunResponse(
                             runInfo: RunInfo
                           )

  object UpdateRunResponse extends MLFlowFormatter {
    implicit val format: OFormat[UpdateRunResponse] = Json.format
  }


}
