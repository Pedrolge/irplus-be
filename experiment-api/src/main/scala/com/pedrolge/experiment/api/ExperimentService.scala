package com.pedrolge.experiment.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{CircuitBreaker, Descriptor, Service, ServiceCall}
import com.pedrolge.experiment.api.model.EmptyResponse
import com.pedrolge.mlflow.api.MLFlow.{MLFlowExceptionSerializer, ResourceAlreadyExistsException, ResourceDoesNotExistException}
import com.pedrolge.mlflow.api.model.ViewType
import com.pedrolge.mlflow.api.model.artifact.Response.ListArtifactsResponse
import com.pedrolge.mlflow.api.model.experiment.Request._
import com.pedrolge.mlflow.api.model.experiment.Response._
import com.pedrolge.mlflow.api.model.metric.Response.GetMetricHistoryResponse
import com.pedrolge.mlflow.api.model.modelVersion.Request._
import com.pedrolge.mlflow.api.model.modelVersion.Response._
import com.pedrolge.mlflow.api.model.registeredModel.Request._
import com.pedrolge.mlflow.api.model.registeredModel.Response._
import com.pedrolge.mlflow.api.model.run.Request._
import com.pedrolge.mlflow.api.model.run.Response._

object ExperimentService  {

}

/**
  * The irplus-be service interface.
  * <p>
  * This describes everything that Lagom needs to know about how to serve and
  * consume the IrplusbeService.
  */
trait ExperimentService extends Service {

  def createExperiment(): ServiceCall[CreateExperimentRequest, CreateExperimentResponse]
  def listExperiment(viewType: ViewType.Value): ServiceCall[NotUsed, ListExperimentResponse]
  def getExperiment(experimentId: String): ServiceCall[NotUsed, GetExperimentResponse]
  def getExperimentByName(experimentName: String): ServiceCall[NotUsed, GetExperimentByNameResponse]
  def deleteExperiment(): ServiceCall[DeleteExperimentRequest, EmptyResponse]
  def restoreExperiment(): ServiceCall[RestoreExperimentRequest, EmptyResponse]
  def updateExperiment(): ServiceCall[UpdateExperimentRequest, EmptyResponse]
  def setExperimentTag(): ServiceCall[SetExperimentTagRequest, EmptyResponse]

  def createRun(): ServiceCall[CreateRunRequest, CreateRunResponse]
  def deleteRun(): ServiceCall[DeleteRunRequest, EmptyResponse]
  def restoreRun(): ServiceCall[RestoreRunRequest, EmptyResponse]
  def getRun(runId: String): ServiceCall[NotUsed, GetRunResponse]
  def logMetric(): ServiceCall[LogMetricRequest, EmptyResponse]
  def logBatch(): ServiceCall[LogBatchRequest, EmptyResponse]
  def logModel(): ServiceCall[LogModelRequest, EmptyResponse]
  def setTag(): ServiceCall[SetTagRequest, EmptyResponse]
  def deleteTag(): ServiceCall[DeleteTagRequest, EmptyResponse]
  def logParameter(): ServiceCall[LogParameterRequest, EmptyResponse]
  def searchRun(): ServiceCall[SearchRunRequest, SearchRunResponse]
  def updateRun(): ServiceCall[UpdateRunRequest, UpdateRunResponse]

  def getMetricHistory(runId: String, metricKey: String): ServiceCall[NotUsed, GetMetricHistoryResponse]

  def listArtifacts(runId: String, path: Option[String], pageToken: Option[String]): ServiceCall[NotUsed, ListArtifactsResponse]

  def createRegisteredModel(): ServiceCall[CreateRegisteredModelRequest, CreateRegisteredModelResponse]
  def getRegisteredModel(name: String): ServiceCall[NotUsed, GetRegisteredModelResponse]
  def renameRegisteredModel(): ServiceCall[RenameRegisteredModelRequest, RenameRegisteredModelResponse]
  def updateRegisteredModel(): ServiceCall[UpdateRegisteredModelRequest, UpdateRegisteredModelResponse]
  def deleteRegisteredModel(): ServiceCall[DeleteRegisteredModelRequest, EmptyResponse]
  def listRegisteredModel(maxResults: Int, pageToken: Option[String]): ServiceCall[NotUsed, ListRegisteredModelResponse]
  def getLatestVersions(name: String, stages: List[String]): ServiceCall[NotUsed, GetLatestVersionsResponse]
  def searchRegisteredModel(): ServiceCall[SearchRegisteredModelRequest, SearchRegisteredModelResponse]
  def setRegisteredModelTag(): ServiceCall[SetRegisteredModelTagRequest, EmptyResponse]
  def deleteRegisteredModelTag(): ServiceCall[DeleteRegisteredModelTagRequest, EmptyResponse]

  def createModelVersion(): ServiceCall[CreateModelVersionRequest, CreateModelVersionResponse]
  def getModelVersion(name: String, version: String): ServiceCall[NotUsed, GetModelVersionResponse]
  def updateModelVersion(): ServiceCall[UpdateModelVersionRequest, UpdateModelVersionResponse]
  def deleteModelVersion(): ServiceCall[DeleteModelVersionRequest, EmptyResponse]
  def searchModelVersion(): ServiceCall[SearchModelVersionRequest, SearchModelVersionResponse]
  def getModelVersionDownloadUri(name: String, version: String): ServiceCall[NotUsed, GetModelVersionDownloadUriResponse]
  def transitionModelVersionStage(): ServiceCall[TransitionModelVersionStageRequest, TransitionModelVersionStageResponse]
  def setModelVersionTag(): ServiceCall[SetModelVersionTagRequest, EmptyResponse]
  def deleteModelVersionTag(): ServiceCall[DeleteModelVersionTagRequest, EmptyResponse]

  override final def descriptor: Descriptor = {
    import Service._
    // @formatter:off
    named("irplus-be")
      .withCalls(
        // EXPERIMENTS
        restCall(Method.POST, "/api/2.0/mlflow/experiments/create", createExperiment _),
        restCall(Method.GET, "/api/2.0/mlflow/experiments/list?view_type", listExperiment _),
        restCall(Method.GET, "/api/2.0/mlflow/experiments/get?experiment_id", getExperiment _),
        restCall(Method.GET, "/api/2.0/mlflow/experiments/get-by-name?experiment_name", getExperimentByName _),
        restCall(Method.POST, "/api/2.0/mlflow/experiments/delete", deleteExperiment _),
        restCall(Method.POST, "/api/2.0/mlflow/experiments/restore", restoreExperiment _),
        restCall(Method.POST, "/api/2.0/mlflow/experiments/update", updateExperiment _),
        restCall(Method.POST, "/api/2.0/mlflow/experiments/set-experiment-tag", setExperimentTag _),

        // RUNS
        restCall(Method.POST, "/api/2.0/mlflow/runs/create", createRun _),
        restCall(Method.POST, "/api/2.0/mlflow/runs/delete", deleteRun _),
        restCall(Method.POST, "/api/2.0/mlflow/runs/restore", restoreRun _),
        restCall(Method.GET, "/api/2.0/mlflow/runs/get?run_id", getRun _),
        restCall(Method.POST, "/api/2.0/mlflow/runs/log-metric", logMetric _),
        restCall(Method.POST, "/api/2.0/mlflow/runs/log-batch", logBatch _),
        restCall(Method.POST, "/api/2.0/mlflow/runs/log-model", logModel _),
        restCall(Method.POST, "/api/2.0/mlflow/runs/set-tag", setTag _),
        restCall(Method.POST, "/api/2.0/mlflow/runs/delete-tag", deleteTag _),
        restCall(Method.POST, "/api/2.0/mlflow/runs/log-parameter", logParameter _),
        restCall(Method.POST, "/api/2.0/mlflow/runs/search", searchRun _),
        restCall(Method.POST, "/api/2.0/mlflow/runs/update", updateRun _),

        // METRICS
        restCall(Method.GET, "/api/2.0/mlflow/metrics/get-history?run_id&metric_key", getMetricHistory _),

        // ARTIFACTS
        restCall(Method.GET, "/api/2.0/mlflow/artifacts/list?run_id&path&page_token", listArtifacts _),

        // REGISTERED MODELS
        restCall(Method.POST, "/api/2.0/preview/mlflow/registered-models/create", createRegisteredModel _),
        restCall(Method.GET, "/api/2.0/preview/mlflow/registered-models/get?name", getRegisteredModel _),
        restCall(Method.POST, "/api/2.0/preview/mlflow/registered-models/rename", renameRegisteredModel _),
        restCall(Method.POST, "/api/2.0/preview/mlflow/registered-models/update", updateRegisteredModel _),
        restCall(Method.DELETE, "/api/2.0/preview/mlflow/registered-models/delete", deleteRegisteredModel _),
        restCall(Method.GET, "/api/2.0/preview/mlflow/registered-models/list?max_results&page_token", listRegisteredModel _),
        restCall(Method.GET, "/api/2.0/preview/mlflow/registered-models/get-latest-versions?name&stages", getLatestVersions _),
        restCall(Method.GET, "/api/2.0/preview/mlflow/registered-models/search", searchRegisteredModel _),
        restCall(Method.GET, "/api/2.0/preview/mlflow/registered-models/set-tag", setRegisteredModelTag _),
        restCall(Method.GET, "/api/2.0/preview/mlflow/registered-models/delete-tag", deleteRegisteredModelTag _),

        // MODEL VERSIONS
        restCall(Method.POST, "/api/2.0/preview/mlflow/model-versions/create", createModelVersion _),
        restCall(Method.GET, "/api/2.0/preview/mlflow/model-versions/get?name&version", getModelVersion _),
        restCall(Method.PATCH, "/api/2.0/preview/mlflow/model-versions/update", updateModelVersion _),
        restCall(Method.POST, "/api/2.0/preview/mlflow/model-versions/delete", deleteModelVersion _),
        restCall(Method.GET, "/api/2.0/preview/mlflow/model-versions/search", searchModelVersion _),
        restCall(Method.GET, "/api/2.0/preview/mlflow/model-versions/get-download-uri?name&version", getModelVersionDownloadUri _),
        restCall(Method.POST, "/api/2.0/preview/mlflow/model-versions/transition-stage", transitionModelVersionStage _),
        restCall(Method.POST, "/api/2.0/preview/mlflow/model-versions/set-tag", setModelVersionTag _),
        restCall(Method.POST, "/api/2.0/preview/mlflow/model-versions/delete-tag", deleteModelVersionTag _)

      )
      .withCircuitBreaker(CircuitBreaker.identifiedBy("experiment-circuit-breaker"))
      .withAutoAcl(true)
      .withExceptionSerializer(
        MLFlowExceptionSerializer(
          ResourceAlreadyExistsException.ErrorCode -> ResourceAlreadyExistsException.formatter,
          ResourceDoesNotExistException.ErrorCode -> ResourceDoesNotExistException.formatter,
        )
      )
    // @formatter:on
  }

}

