package com.pedrolge.experiment.impl

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.transport.{RequestHeader, ResponseHeader}
import com.lightbend.lagom.scaladsl.server.ServerServiceCall
import com.pedrolge.experiment.api.ExperimentService
import com.pedrolge.experiment.api.model.EmptyResponse
import com.pedrolge.experiment.api.model.auth._
import com.pedrolge.experiment.api.util.Exception.Conflict
import com.pedrolge.experiment.impl.auth.{AuthComponent, AuthConfig}
import com.pedrolge.experiment.impl.repo.{ApiTokenRepository, UserRepository}
import com.pedrolge.mlflow
import com.pedrolge.mlflow.api.model.artifact.Response._
import com.pedrolge.mlflow.api.model.experiment.Request._
import com.pedrolge.mlflow.api.model.experiment.Response._
import com.pedrolge.mlflow.api.model.metric.Response._
import com.pedrolge.mlflow.api.model.modelVersion.Request._
import com.pedrolge.mlflow.api.model.modelVersion.Response._
import com.pedrolge.mlflow.api.model.registeredModel.Request._
import com.pedrolge.mlflow.api.model.registeredModel.Response._
import com.pedrolge.mlflow.api.model.run.Request._
import com.pedrolge.mlflow.api.model.run.Response._
import com.typesafe.scalalogging.Logger
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer.requireAnyRole
import org.pac4j.core.profile.CommonProfile
import org.postgresql.util.PSQLException

import scala.concurrent.ExecutionContext

/**
 * Implementation of the IrplusbeService.
 */
class ExperimentServiceImpl(val operationRegistry: OperationRegistry,
                            val authConfig: AuthConfig,
                            val userRepository: UserRepository,
                            val apiTokenRepository: ApiTokenRepository)(implicit val ec: ExecutionContext)
  extends ExperimentService
    with AuthComponent {

  val logger: Logger = Logger(classOf[ExperimentServiceImpl])

  override def createToken(): ServiceCall[CreateTokenRequest, CreateTokenResponse] = {
    authorize(requireAnyRole[CommonProfile]("operator"), (profile: CommonProfile) =>
      ServerServiceCall {
        (_, request: CreateTokenRequest) =>
          operationRegistry
            .createTokenOperation
            .process(profile.getId, request)
            .map { result =>
              (ResponseHeader.Ok, result)
            }
            .recover {
              case _: PSQLException =>
                throw Conflict("A token with this name already exists")
            }
      }
    )
  }

  override def listToken(): ServiceCall[NotUsed, ListTokenResponse] = {
    authorize(requireAnyRole[CommonProfile]("operator"), (profile: CommonProfile) =>
      ServerServiceCall {
        (_, _: NotUsed) =>
          operationRegistry
            .listTokenOperation
            .process(profile.getId)
            .map { result =>
              (ResponseHeader.Ok, result)
            }
      }
    )
  }


  override def createExperiment(): ServiceCall[CreateExperimentRequest, CreateExperimentResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: CreateExperimentRequest) =>
          operationRegistry
            .createExperimentOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, result)
            }
      }
    )


  override def listExperiment(viewType: mlflow.api.model.ViewType.Value): ServiceCall[NotUsed, ListExperimentResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_: RequestHeader, _: NotUsed) =>
          operationRegistry
            .listExperimentsOperation
            .process(viewType)
            .map { result =>
              (ResponseHeader.Ok, result)
            }
      }
    )


  override def getExperiment(experimentId: String): ServiceCall[NotUsed, GetExperimentResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_: RequestHeader, _: NotUsed) =>
          operationRegistry
            .getExperimentOperation
            .process(experimentId = experimentId)
            .map { result =>
              (ResponseHeader.Ok, result)
            }
      }
    )

  override def getExperimentByName(experimentName: String): ServiceCall[NotUsed, GetExperimentByNameResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, _: NotUsed) =>
          operationRegistry
            .getExperimentByNameOperation
            .process(experimentName)
            .map { result =>
              (ResponseHeader.Ok, result)
            }
      }
    )

  override def deleteExperiment(): ServiceCall[DeleteExperimentRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: DeleteExperimentRequest) =>
          operationRegistry
            .deleteExperimentOperation
            .process(request)
            .map { _ =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def restoreExperiment(): ServiceCall[RestoreExperimentRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: RestoreExperimentRequest) =>
          operationRegistry
            .restoreExperimentOperation
            .process(request)
            .map { _ =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def updateExperiment(): ServiceCall[UpdateExperimentRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: UpdateExperimentRequest) =>
          operationRegistry
            .updateExperimentOperation
            .process(request)
            .map { _ =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def setExperimentTag(): ServiceCall[SetExperimentTagRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: SetExperimentTagRequest) =>
          operationRegistry
            .setExperimentTagOperation
            .process(request)
            .map { _ =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def createRun(): ServiceCall[CreateRunRequest, CreateRunResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: CreateRunRequest) =>
          operationRegistry
            .createRunOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def deleteRun(): ServiceCall[DeleteRunRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: DeleteRunRequest) =>
          operationRegistry
            .deleteRunOperation
            .process(request)
            .map { _ =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def restoreRun(): ServiceCall[RestoreRunRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: RestoreRunRequest) =>
          operationRegistry
            .restoreRunOperation
            .process(request)
            .map { _ =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def getRun(runId: String): ServiceCall[NotUsed, GetRunResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, _: NotUsed) =>
          operationRegistry
            .getRunOperation
            .process(runId)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def logMetric(): ServiceCall[LogMetricRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: LogMetricRequest) =>
          operationRegistry
            .logMetricOperation
            .process(request)
            .map { _ =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def logBatch(): ServiceCall[LogBatchRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: LogBatchRequest) =>
          operationRegistry
            .logBatchOperation
            .process(request)
            .map { _ =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def logModel(): ServiceCall[LogModelRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: LogModelRequest) =>
          operationRegistry
            .logModelOperation
            .process(request)
            .map { _ =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def setTag(): ServiceCall[SetTagRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: SetTagRequest) =>
          operationRegistry
            .setTagOperation
            .process(request)
            .map { _ =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def deleteTag(): ServiceCall[DeleteTagRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: DeleteTagRequest) =>
          operationRegistry
            .deleteTagOperation
            .process(request)
            .map { _ =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def logParameter(): ServiceCall[LogParameterRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: LogParameterRequest) =>
          operationRegistry
            .logParameterOperation
            .process(request)
            .map { _ =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def searchRun(): ServiceCall[SearchRunRequest, SearchRunResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: SearchRunRequest) =>
          operationRegistry
            .searchRunOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def updateRun(): ServiceCall[UpdateRunRequest, UpdateRunResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: UpdateRunRequest) =>
          operationRegistry
            .updateRunOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def getMetricHistory(runId: String, metricKey: String): ServiceCall[NotUsed, GetMetricHistoryResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, _: NotUsed) =>
          operationRegistry
            .getMetricHistoryOperation
            .process(runId, metricKey)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def listArtifacts(runId: String, path: Option[String], pageToken: Option[String]): ServiceCall[NotUsed, ListArtifactsResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, _: NotUsed) =>
          operationRegistry
            .listArtifactsOperation
            .process(runId, path, pageToken)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }

    )

  override def createRegisteredModel(): ServiceCall[CreateRegisteredModelRequest, CreateRegisteredModelResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: CreateRegisteredModelRequest) =>
          operationRegistry
            .createRegisteredModelOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def getRegisteredModel(name: String): ServiceCall[NotUsed, GetRegisteredModelResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: NotUsed) =>
          operationRegistry
            .getRegisteredModelOperation
            .process(name)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def renameRegisteredModel(): ServiceCall[RenameRegisteredModelRequest, RenameRegisteredModelResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: RenameRegisteredModelRequest) =>
          operationRegistry
            .renameRegisteredModelOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def updateRegisteredModel(): ServiceCall[UpdateRegisteredModelRequest, UpdateRegisteredModelResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: UpdateRegisteredModelRequest) =>
          operationRegistry
            .updateRegisteredModelOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def deleteRegisteredModel(): ServiceCall[DeleteRegisteredModelRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: DeleteRegisteredModelRequest) =>
          operationRegistry
            .deleteRegisteredModelOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def listRegisteredModel(maxResults: Int, pageToken: Option[String]): ServiceCall[NotUsed, ListRegisteredModelResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: NotUsed) =>
          operationRegistry
            .listRegisteredModelOperation
            .process(maxResults, pageToken)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def getLatestVersions(name: String, stages: List[String]): ServiceCall[NotUsed, GetLatestVersionsResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: NotUsed) =>
          operationRegistry
            .getLatestVersionsOperation
            .process(name, stages)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def searchRegisteredModel(): ServiceCall[SearchRegisteredModelRequest, SearchRegisteredModelResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: SearchRegisteredModelRequest) =>
          operationRegistry
            .searchRegisteredModelOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def setRegisteredModelTag(): ServiceCall[SetRegisteredModelTagRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: SetRegisteredModelTagRequest) =>
          operationRegistry
            .setRegisteredModelTagOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def deleteRegisteredModelTag(): ServiceCall[DeleteRegisteredModelTagRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: DeleteRegisteredModelTagRequest) =>
          operationRegistry
            .deleteRegisteredModelTagOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def createModelVersion(): ServiceCall[CreateModelVersionRequest, CreateModelVersionResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: CreateModelVersionRequest) =>
          operationRegistry
            .createModelVersionOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def getModelVersion(name: String, version: String): ServiceCall[NotUsed, GetModelVersionResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: NotUsed) =>
          operationRegistry
            .getModelVersionOperation
            .process(name, version)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def updateModelVersion(): ServiceCall[UpdateModelVersionRequest, UpdateModelVersionResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: UpdateModelVersionRequest) =>
          operationRegistry
            .updateModelVersionOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def deleteModelVersion(): ServiceCall[DeleteModelVersionRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: DeleteModelVersionRequest) =>
          operationRegistry
            .deleteModelVersionOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def searchModelVersion(): ServiceCall[SearchModelVersionRequest, SearchModelVersionResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: SearchModelVersionRequest) =>
          operationRegistry
            .searchModelVersionOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def getModelVersionDownloadUri(name: String, version: String): ServiceCall[NotUsed, GetModelVersionDownloadUriResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: NotUsed) =>
          operationRegistry
            .getModelVersionDownloadUriOperation
            .process(name, version)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def transitionModelVersionStage(): ServiceCall[TransitionModelVersionStageRequest, TransitionModelVersionStageResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: TransitionModelVersionStageRequest) =>
          operationRegistry
            .transitionModelVersionStageOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, result)
            }

      }
    )

  override def setModelVersionTag(): ServiceCall[SetModelVersionTagRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: SetModelVersionTagRequest) =>
          operationRegistry
            .setModelVersionTagOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, EmptyResponse())
            }

      }
    )

  override def deleteModelVersionTag(): ServiceCall[DeleteModelVersionTagRequest, EmptyResponse] =
    authorize(requireAnyRole[CommonProfile]("operator", "api-key"), (_: CommonProfile) =>
      ServerServiceCall {
        (_, request: DeleteModelVersionTagRequest) =>
          operationRegistry
            .deleteModelVersionTagOperation
            .process(request)
            .map { result =>
              (ResponseHeader.Ok, EmptyResponse())
            }
      }
    )

}
