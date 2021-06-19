package com.pedrolge.experiment.impl

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.transport.{RequestHeader, ResponseHeader}
import com.lightbend.lagom.scaladsl.server.ServerServiceCall
import com.pedrolge.experiment.api.ExperimentService
import com.pedrolge.experiment.api.model.EmptyResponse
import com.pedrolge.mlflow.api.model
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
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer.requireAnyRole
import org.pac4j.core.config.Config
import org.pac4j.core.profile.CommonProfile
import org.pac4j.lagom.scaladsl.SecuredService

import scala.concurrent.ExecutionContext

/**
 * Implementation of the IrplusbeService.
 */
class ExperimentServiceImpl(val operationRegistry: OperationRegistry, val securityConfig: Config)(implicit ec: ExecutionContext)
  extends ExperimentService
    with SecuredService {

  override def createExperiment(): ServiceCall[CreateExperimentRequest, CreateExperimentResponse] =
    ServerServiceCall {
      (_, request) =>
        operationRegistry
          .createExperimentOperation
          .process(request)
          .map { result =>
            (ResponseHeader.Ok, result)
          }

    }

  override def listExperiment(viewType: model.ViewType.Value): ServiceCall[NotUsed, ListExperimentResponse] =
    authorize(requireAnyRole[CommonProfile]("operator"), (_: CommonProfile) =>
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
        authenticate { _ : CommonProfile =>
          ServerServiceCall {
          (_: RequestHeader, _: NotUsed) =>
            operationRegistry
              .getExperimentOperation
              .process(experimentId = experimentId)
              .map { result =>
                (ResponseHeader.Ok, result)
              }
        }
  }

  override def getExperimentByName(experimentName: String): ServiceCall[NotUsed, GetExperimentByNameResponse] = ServerServiceCall {
    (_, _) =>
      operationRegistry
        .getExperimentByNameOperation
        .process(experimentName)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def deleteExperiment(): ServiceCall[DeleteExperimentRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .deleteExperimentOperation
        .process(request)
        .map { _ =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def restoreExperiment(): ServiceCall[RestoreExperimentRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .restoreExperimentOperation
        .process(request)
        .map { _ =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def updateExperiment(): ServiceCall[UpdateExperimentRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .updateExperimentOperation
        .process(request)
        .map { _ =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def setExperimentTag(): ServiceCall[SetExperimentTagRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .setExperimentTagOperation
        .process(request)
        .map { _ =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def createRun(): ServiceCall[CreateRunRequest, CreateRunResponse] = ServerServiceCall {
    (header, request) =>

      operationRegistry
        .createRunOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def deleteRun(): ServiceCall[DeleteRunRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .deleteRunOperation
        .process(request)
        .map { _ =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def restoreRun(): ServiceCall[RestoreRunRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .restoreRunOperation
        .process(request)
        .map { _ =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def getRun(runId: String): ServiceCall[NotUsed, GetRunResponse] = ServerServiceCall {
    (_, _) =>
      operationRegistry
        .getRunOperation
        .process(runId)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def logMetric(): ServiceCall[LogMetricRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .logMetricOperation
        .process(request)
        .map { _ =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def logBatch(): ServiceCall[LogBatchRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .logBatchOperation
        .process(request)
        .map { _ =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def logModel(): ServiceCall[LogModelRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .logModelOperation
        .process(request)
        .map { _ =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def setTag(): ServiceCall[SetTagRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .setTagOperation
        .process(request)
        .map { _ =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def deleteTag(): ServiceCall[DeleteTagRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .deleteTagOperation
        .process(request)
        .map { _ =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def logParameter(): ServiceCall[LogParameterRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .logParameterOperation
        .process(request)
        .map { _ =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def searchRun(): ServiceCall[SearchRunRequest, SearchRunResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .searchRunOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def updateRun(): ServiceCall[UpdateRunRequest, UpdateRunResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .updateRunOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def getMetricHistory(runId: String, metricKey: String): ServiceCall[NotUsed, GetMetricHistoryResponse] = ServerServiceCall {
    (_, _) =>
      operationRegistry
        .getMetricHistoryOperation
        .process(runId, metricKey)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def listArtifacts(runId: String, path: Option[String], pageToken: Option[String]): ServiceCall[NotUsed, ListArtifactsResponse] = ServerServiceCall {
    (_, _) =>
      operationRegistry
        .listArtifactsOperation
        .process(runId, path, pageToken)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }


  override def createRegisteredModel(): ServiceCall[CreateRegisteredModelRequest, CreateRegisteredModelResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .createRegisteredModelOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def getRegisteredModel(name: String): ServiceCall[NotUsed, GetRegisteredModelResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .getRegisteredModelOperation
        .process(name)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def renameRegisteredModel(): ServiceCall[RenameRegisteredModelRequest, RenameRegisteredModelResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .renameRegisteredModelOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def updateRegisteredModel(): ServiceCall[UpdateRegisteredModelRequest, UpdateRegisteredModelResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .updateRegisteredModelOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def deleteRegisteredModel(): ServiceCall[DeleteRegisteredModelRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .deleteRegisteredModelOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def listRegisteredModel(maxResults: Int, pageToken: Option[String]): ServiceCall[NotUsed, ListRegisteredModelResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .listRegisteredModelOperation
        .process(maxResults, pageToken)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def getLatestVersions(name: String, stages: List[String]): ServiceCall[NotUsed, GetLatestVersionsResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .getLatestVersionsOperation
        .process(name, stages)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def searchRegisteredModel(): ServiceCall[SearchRegisteredModelRequest, SearchRegisteredModelResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .searchRegisteredModelOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def setRegisteredModelTag(): ServiceCall[SetRegisteredModelTagRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .setRegisteredModelTagOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def deleteRegisteredModelTag(): ServiceCall[DeleteRegisteredModelTagRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .deleteRegisteredModelTagOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def createModelVersion(): ServiceCall[CreateModelVersionRequest, CreateModelVersionResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .createModelVersionOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def getModelVersion(name: String, version: String): ServiceCall[NotUsed, GetModelVersionResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .getModelVersionOperation
        .process(name, version)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def updateModelVersion(): ServiceCall[UpdateModelVersionRequest, UpdateModelVersionResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .updateModelVersionOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def deleteModelVersion(): ServiceCall[DeleteModelVersionRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .deleteModelVersionOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def searchModelVersion(): ServiceCall[SearchModelVersionRequest, SearchModelVersionResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .searchModelVersionOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def getModelVersionDownloadUri(name: String, version: String): ServiceCall[NotUsed, GetModelVersionDownloadUriResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .getModelVersionDownloadUriOperation
        .process(name, version)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def transitionModelVersionStage(): ServiceCall[TransitionModelVersionStageRequest, TransitionModelVersionStageResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .transitionModelVersionStageOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, result)
        }

  }

  override def setModelVersionTag(): ServiceCall[SetModelVersionTagRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .setModelVersionTagOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }

  override def deleteModelVersionTag(): ServiceCall[DeleteModelVersionTagRequest, EmptyResponse] = ServerServiceCall {
    (_, request) =>
      operationRegistry
        .deleteModelVersionTagOperation
        .process(request)
        .map { result =>
          (ResponseHeader.Ok, EmptyResponse())
        }

  }
}
