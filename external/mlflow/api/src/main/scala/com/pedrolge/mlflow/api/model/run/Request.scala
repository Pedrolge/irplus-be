package com.pedrolge.mlflow.api.model.run

import com.pedrolge.mlflow.api.model.RunStatus.RunStatus
import com.pedrolge.mlflow.api.model._
import play.api.libs.json.{Json, OFormat}

object Request {

  case class CreateRunRequest(
                             experimentId: String,
                             userId: String,
                             startTime: Option[String],
                             tags: List[RunTag]
                           )

  object CreateRunRequest extends MLFlowFormatter {
    implicit val format: OFormat[CreateRunRequest] = Json.format
  }

  case class DeleteRunRequest(
                               runId: String
                             )

  object DeleteRunRequest extends MLFlowFormatter {
    implicit val format: OFormat[DeleteRunRequest] = Json.format
  }

  case class RestoreRunRequest(
                               runId: String
                             )

  object RestoreRunRequest extends MLFlowFormatter {
    implicit val format: OFormat[RestoreRunRequest] = Json.format
  }

  case class LogMetricRequest(
                               runId: String,
                               key: String,
                               value: Double,
                               timestamp: String,
                               step: Option[String]
                             )

  object LogMetricRequest extends MLFlowFormatter {
    implicit val format: OFormat[LogMetricRequest] = Json.format
  }

  case class LogBatchRequest(
                               runId: String,
                               metrics: List[Metric],
                               params: List[Param],
                               tags: List[RunTag]
                             )

  object LogBatchRequest extends MLFlowFormatter {
    implicit val format: OFormat[LogBatchRequest] = Json.format
  }

  case class LogModelRequest(
                               runId: String,
                               modelJson: String
                             )

  object LogModelRequest extends MLFlowFormatter {
    implicit val format: OFormat[LogModelRequest] = Json.format
  }

  case class SetTagRequest(
                               runId: String,
                               key: String,
                               value: String
                             )

  object SetTagRequest extends MLFlowFormatter {
    implicit val format: OFormat[SetTagRequest] = Json.format
  }

  case class DeleteTagRequest(
                               runId: String,
                               key: String
                             )

  object DeleteTagRequest extends MLFlowFormatter {
    implicit val format: OFormat[DeleteTagRequest] = Json.format
  }


  case class LogParameterRequest(
                               runId: String,
                               key: String,
                               value: String
                             )

  object LogParameterRequest extends MLFlowFormatter {
    implicit val format: OFormat[LogParameterRequest] = Json.format
  }

  case class SearchRunRequest(
                               experimentIds: List[String],
                               filter: String,
                               runViewType: ViewType.Value,
                               maxResults: Int,
                               orderBy: List[String],
                               pageToken: Option[String]
                             )

  object SearchRunRequest extends MLFlowFormatter {
    implicit val format: OFormat[SearchRunRequest] = Json.format
  }

  case class UpdateRunRequest(
                               runId: String,
                               status: RunStatus,
                               endTime: String
                             )

  object UpdateRunRequest extends MLFlowFormatter {
    implicit val format: OFormat[UpdateRunRequest] = Json.format
  }
}
