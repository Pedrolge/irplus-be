package com.pedrolge.mlflow.api

import com.lightbend.lagom.scaladsl.api.deser.PathParamSerializer
import com.pedrolge.mlflow.api.model.ModelVersionStatus.ModelVersionStatus
import com.pedrolge.mlflow.api.model.RunStatus.RunStatus
import play.api.libs.json.JsonConfiguration.Aux
import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration, OFormat, Reads, Writes}

package object model {

  case class Experiment(
                         experimentId: String,
                         name: String,
                         artifactLocation: String,
                         lifecycleStage: String,
                         lastUpdateTime: Option[String],
                         creationTime: Option[String],
                       )

  object Experiment extends MLFlowFormatter {
    implicit val formatter: OFormat[Experiment] = Json.format
  }

  case class ExperimentTag(
                            key: String,
                            value: String
                          )

  object ExperimentTag extends MLFlowFormatter {
    implicit val formatter: OFormat[ExperimentTag] = Json.format
  }

  case class FileInfo(
                       path: String,
                       isDir: Boolean,
                       fileSize: Long
                     )

  object FileInfo extends MLFlowFormatter {
    implicit val formatter: OFormat[FileInfo] = Json.format
  }

  case class Metric(
                     key: String,
                     value: Double,
                     timestamp: String,
                     step: Option[String]
                   )

  object Metric extends MLFlowFormatter {
    implicit val formatter: OFormat[Metric] = Json.format
  }

  case class ModelVersion(
                           name: String,
                           version: String,
                           creationTimestamp: String,
                           lastUpdatedTimestamp: String,
                           userId: Option[String],
                           currentStage: String,
                           description: Option[String],
                           source: Option[String],
                           runId: String,
                           status: ModelVersionStatus,
                           statusMessage: Option[String],
                           tags: Option[List[ModelVersionTag]],
                           runLink: String
                         )

  object ModelVersion extends MLFlowFormatter {
    implicit val formatter: OFormat[ModelVersion] = Json.format
  }

  case class ModelVersionTag(
                              key: String,
                              value: String
                            )

  object ModelVersionTag extends MLFlowFormatter {
    implicit val formatter: OFormat[ModelVersionTag] = Json.format
  }

  case class Param(
                    key: String,
                    value: String
                  )

  object Param extends MLFlowFormatter {
    implicit val formatter: OFormat[Param] = Json.format
  }

  case class RegisteredModel(
                              name: String,
                              creationTimestamp: String,
                              lastUpdatedTimestamp: String,
                              userId: String,
                              description: String,
                              latestVersions: List[ModelVersion],
                              tags: Option[List[RegisteredModelTag]]
                            )

  object RegisteredModel extends MLFlowFormatter {
    implicit val formatter: OFormat[RegisteredModel] = Json.format
  }

  case class RegisteredModelTag(
                                 key: String,
                                 value: String
                               )

  object RegisteredModelTag extends MLFlowFormatter {
    implicit val formatter: OFormat[RegisteredModelTag] = Json.format
  }

  case class Run(
                  info: RunInfo,
                  data: RunData
                )

  object Run extends MLFlowFormatter {
    implicit val formatter: OFormat[Run] = Json.format
  }

  case class RunInfo(
                      runId: String,
                      experimentId: String,
                      status: RunStatus,
                      startTime: Option[String],
                      endTime: Option[String],
                      artifactUri: String,
                      lifecycleStage: String
                    )

  object RunInfo extends MLFlowFormatter {
    implicit val formatter: OFormat[RunInfo] = Json.format
  }

  case class RunData(
                      metrics: Option[List[Metric]],
                      params: Option[List[Param]],
                      tags: Option[List[RunTag]]
                    )

  object RunData extends MLFlowFormatter {
    implicit val formatter: OFormat[RunData] = Json.format
  }

  case class RunTag(
                     key: String,
                     value: String
                   )

  object RunTag extends MLFlowFormatter {
    implicit val formatter: OFormat[RunTag] = Json.format
  }

  object RunStatus extends Enumeration {
    type RunStatus = Value
    val RUNNING, SCHEDULED, FINISHED, FAILED, KILLED = Value

    implicit lazy val formatterReads: Reads[RunStatus] = Reads.enumNameReads(RunStatus)
    implicit lazy val formatterWrites: Writes[RunStatus] = Writes.enumNameWrites
  }

  object ModelVersionStatus extends Enumeration {
    type ModelVersionStatus = Value
    val PENDING_REGISTRATION, FAILED_REGISTRATION, READY = Value

    implicit lazy val formatterReads: Reads[ModelVersionStatus] = Reads.enumNameReads(ModelVersionStatus)
    implicit lazy val formatterWrites: Writes[ModelVersionStatus] = Writes.enumNameWrites
  }

  object ViewType extends Enumeration {
    val ACTIVE_ONLY, DELETED_ONLY, ALL = Value

    implicit lazy val formatterReads: Reads[ViewType.Value] = Reads.enumNameReads(ViewType)
    implicit lazy val formatterWrites: Writes[ViewType.Value] = Writes.enumNameWrites

    implicit val statusPathParamSerializer: PathParamSerializer[ViewType.Value] =
      PathParamSerializer.required("view_type")(ViewType.withName)(_.toString)
  }


  trait MLFlowFormatter {
    implicit val config: Aux[Json.MacroOptions] = JsonConfiguration(SnakeCase)
  }

}
