package com.pedrolge.mlflow.api

import akka.NotUsed
import akka.util.ByteString
import com.lightbend.lagom.scaladsl.api.Service.{named, restCall}
import com.lightbend.lagom.scaladsl.api.deser.{DefaultExceptionSerializer, ExceptionSerializer, RawExceptionMessage}
import com.lightbend.lagom.scaladsl.api.transport._
import com.lightbend.lagom.scaladsl.api.{CircuitBreaker, Descriptor, Service, ServiceCall}
import com.pedrolge.mlflow.api.MLFlow.{MLFlowExceptionSerializer, ResourceAlreadyExistsException, ResourceDoesNotExistException}
import com.pedrolge.mlflow.api.model.experiment.Request._
import com.pedrolge.mlflow.api.model.experiment.Response._
import com.pedrolge.mlflow.api.model.run.Request._
import com.pedrolge.mlflow.api.model.run.Response._
import com.pedrolge.mlflow.api.model.modelVersion.Request._
import com.pedrolge.mlflow.api.model.modelVersion.Response._
import com.pedrolge.mlflow.api.model.registeredModel.Request._
import com.pedrolge.mlflow.api.model.registeredModel.Response._
import com.pedrolge.mlflow.api.model.artifact.Response._
import com.pedrolge.mlflow.api.model.metric.Response._
import com.pedrolge.mlflow.api.model.{MLFlowFormatter, ViewType}
import play.api.Environment
import play.api.libs.json.{JsString, Json, OFormat}

import scala.collection.immutable
import scala.util.control.NonFatal

trait MLFlow extends Service {

  def createExperiment(): ServiceCall[CreateExperimentRequest, CreateExperimentResponse]

  def listExperiment(viewType: ViewType.Value): ServiceCall[NotUsed, ListExperimentResponse]

  def getExperiment(experimentId: String): ServiceCall[NotUsed, GetExperimentResponse]

  def getExperimentByName(experimentName: String): ServiceCall[NotUsed, GetExperimentByNameResponse]

  def deleteExperiment(): ServiceCall[DeleteExperimentRequest, NotUsed]

  def restoreExperiment(): ServiceCall[RestoreExperimentRequest, NotUsed]

  def updateExperiment(): ServiceCall[UpdateExperimentRequest, NotUsed]

  def setExperimentTag(): ServiceCall[SetExperimentTagRequest, NotUsed]

  def createRun(): ServiceCall[CreateRunRequest, CreateRunResponse]

  def deleteRun(): ServiceCall[DeleteRunRequest, NotUsed]

  def restoreRun(): ServiceCall[RestoreRunRequest, NotUsed]

  def getRun(runId: String): ServiceCall[NotUsed, GetRunResponse]

  def logMetric(): ServiceCall[LogMetricRequest, NotUsed]

  def logBatch(): ServiceCall[LogBatchRequest, NotUsed]

  def logModel(): ServiceCall[LogModelRequest, NotUsed]

  def setTag(): ServiceCall[SetTagRequest, NotUsed]

  def deleteTag(): ServiceCall[DeleteTagRequest, NotUsed]

  def logParameter(): ServiceCall[LogParameterRequest, NotUsed]

  def searchRun(): ServiceCall[SearchRunRequest, SearchRunResponse]

  def updateRun(): ServiceCall[UpdateRunRequest, UpdateRunResponse]

  def getMetricHistory(runId: String, metricKey: String): ServiceCall[NotUsed, GetMetricHistoryResponse]

  def listArtifacts(runId: String, path: Option[String], pageToken: Option[String]): ServiceCall[NotUsed, ListArtifactsResponse]

  def createRegisteredModel(): ServiceCall[CreateRegisteredModelRequest, CreateRegisteredModelResponse]

  def getRegisteredModel(name: String): ServiceCall[NotUsed, GetRegisteredModelResponse]

  def renameRegisteredModel(): ServiceCall[RenameRegisteredModelRequest, RenameRegisteredModelResponse]

  def updateRegisteredModel(): ServiceCall[UpdateRegisteredModelRequest, UpdateRegisteredModelResponse]

  def deleteRegisteredModel(): ServiceCall[DeleteRegisteredModelRequest, NotUsed]

  def listRegisteredModel(maxResults: Int, pageToken: Option[String]): ServiceCall[NotUsed, ListRegisteredModelResponse]

  def getLatestVersions(name: String, stages: List[String]): ServiceCall[NotUsed, GetLatestVersionsResponse]

  def searchRegisteredModel(): ServiceCall[SearchRegisteredModelRequest, SearchRegisteredModelResponse]

  def setRegisteredModelTag(): ServiceCall[SetRegisteredModelTagRequest, NotUsed]

  def deleteRegisteredModelTag(): ServiceCall[DeleteRegisteredModelTagRequest, NotUsed]

  def createModelVersion(): ServiceCall[CreateModelVersionRequest, CreateModelVersionResponse]

  def getModelVersion(name: String, version: String): ServiceCall[NotUsed, GetModelVersionResponse]

  def updateModelVersion(): ServiceCall[UpdateModelVersionRequest, UpdateModelVersionResponse]

  def deleteModelVersion(): ServiceCall[DeleteModelVersionRequest, NotUsed]

  def searchModelVersion(): ServiceCall[SearchModelVersionRequest, SearchModelVersionResponse]

  def getModelVersionDownloadUri(name: String, version: String): ServiceCall[NotUsed, GetModelVersionDownloadUriResponse]

  def transitionModelVersionStage(): ServiceCall[TransitionModelVersionStageRequest, TransitionModelVersionStageResponse]

  def setModelVersionTag(): ServiceCall[SetModelVersionTagRequest, NotUsed]

  def deleteModelVersionTag(): ServiceCall[DeleteModelVersionTagRequest, NotUsed]


  override def descriptor: Descriptor =
    named(MLFlow.name)
      .withCalls(
        // EXPERIMENTS
        restCall(Method.POST, "/2.0/mlflow/experiments/create", createExperiment _),
        restCall(Method.GET, "/2.0/mlflow/experiments/list?view_type", listExperiment _),
        restCall(Method.GET, "/2.0/mlflow/experiments/get?experiment_id", getExperiment _),
        restCall(Method.GET, "/2.0/mlflow/experiments/get-by-name?experiment_name", getExperimentByName _),
        restCall(Method.POST, "/2.0/mlflow/experiments/delete", deleteExperiment _),
        restCall(Method.POST, "/2.0/mlflow/experiments/restore", restoreExperiment _),
        restCall(Method.POST, "/2.0/mlflow/experiments/update", updateExperiment _),
        restCall(Method.POST, "/2.0/mlflow/experiments/set-experiment-tag", setExperimentTag _),

        // RUNS
        restCall(Method.POST, "/2.0/mlflow/runs/create", createRun _),
        restCall(Method.POST, "/2.0/mlflow/runs/delete", deleteRun _),
        restCall(Method.POST, "/2.0/mlflow/runs/restore", restoreRun _),
        restCall(Method.GET, "/2.0/mlflow/runs/get?run_id", getRun _),
        restCall(Method.POST, "/2.0/mlflow/runs/log-metric", logMetric _),
        restCall(Method.POST, "/2.0/mlflow/runs/log-batch", logBatch _),
        restCall(Method.POST, "/2.0/mlflow/runs/log-model", logModel _),
        restCall(Method.POST, "/2.0/mlflow/runs/set-tag", setTag _),
        restCall(Method.POST, "/2.0/mlflow/runs/delete-tag", deleteTag _),
        restCall(Method.POST, "/2.0/mlflow/runs/log-parameter", logParameter _),
        restCall(Method.POST, "/2.0/mlflow/runs/search", searchRun _),
        restCall(Method.POST, "/2.0/mlflow/runs/update", updateRun _),

        // METRICS
        restCall(Method.GET, "/2.0/mlflow/metrics/get-history?run_id&metric_key", getMetricHistory _),

        // ARTIFACTS
        restCall(Method.GET, "/2.0/mlflow/artifacts/list?run_id&path&page_token", listArtifacts _),

        // REGISTERED MODELS
        restCall(Method.POST, "/2.0/preview/mlflow/registered-models/create", createRegisteredModel _),
        restCall(Method.GET, "/2.0/preview/mlflow/registered-models/get?name", getRegisteredModel _),
        restCall(Method.POST, "/2.0/preview/mlflow/registered-models/rename", renameRegisteredModel _),
        restCall(Method.POST, "/2.0/preview/mlflow/registered-models/update", updateRegisteredModel _),
        restCall(Method.DELETE, "/2.0/preview/mlflow/registered-models/delete", deleteRegisteredModel _),
        restCall(Method.GET, "/2.0/preview/mlflow/registered-models/list?max_results&page_token", listRegisteredModel _),
        restCall(Method.GET, "/2.0/preview/mlflow/registered-models/get-latest-versions?name&stages", getLatestVersions _),
        restCall(Method.GET, "/2.0/preview/mlflow/registered-models/search", searchRegisteredModel _),
        restCall(Method.GET, "/2.0/preview/mlflow/registered-models/set-tag", setRegisteredModelTag _),
        restCall(Method.GET, "/2.0/preview/mlflow/registered-models/delete-tag", deleteRegisteredModelTag _),

        // MODEL VERSIONS
        restCall(Method.POST, "/2.0/preview/mlflow/model-versions/create", createModelVersion _),
        restCall(Method.GET, "/2.0/preview/mlflow/model-versions/get?name&version", getModelVersion _),
        restCall(Method.PATCH, "/2.0/preview/mlflow/model-versions/update", updateModelVersion _),
        restCall(Method.POST, "/2.0/preview/mlflow/model-versions/delete", deleteModelVersion _),
        restCall(Method.GET, "/2.0/preview/mlflow/model-versions/search", searchModelVersion _),
        restCall(Method.GET, "/2.0/preview/mlflow/model-versions/get-download-uri?name&version", getModelVersionDownloadUri _),
        restCall(Method.POST, "/2.0/preview/mlflow/model-versions/transition-stage", transitionModelVersionStage _),
        restCall(Method.POST, "/2.0/preview/mlflow/model-versions/set-tag", setModelVersionTag _),
        restCall(Method.POST, "/2.0/preview/mlflow/model-versions/delete-tag", deleteModelVersionTag _),

      )
      .withCircuitBreaker(CircuitBreaker.identifiedBy("mlflow-circuit-breaker"))
      .withAutoAcl(true)
      .withExceptionSerializer(
        MLFlowExceptionSerializer(
          ResourceAlreadyExistsException.ErrorCode -> ResourceAlreadyExistsException.formatter,
          ResourceDoesNotExistException.ErrorCode -> ResourceDoesNotExistException.formatter,
        )
      )
}


object MLFlow {
  val name = "mlflow"


  case class ResourceDoesNotExistException(errorCode: String, message: String) extends MLFlowException

  object ResourceDoesNotExistException extends MLFlowFormatter {
    val ErrorCode = "RESOURCE_DOES_NOT_EXIST"
    val ErrorCodeResponse = TransportErrorCode.NotFound

    implicit val formatter: OFormat[ResourceDoesNotExistException] = Json.format
  }

  case class ResourceAlreadyExistsException(errorCode: String, message: String) extends MLFlowException

  object ResourceAlreadyExistsException extends MLFlowFormatter {
    val ErrorCode = "RESOURCE_ALREADY_EXISTS"
    val ErrorCodeResponse = TransportErrorCode.BadRequest

    implicit val formatter: OFormat[ResourceAlreadyExistsException] = Json.format
  }

  case class MLFlowExceptionSerializer(mlflowExceptions: (String, OFormat[_ <: MLFlowException])*) extends DefaultExceptionSerializer(Environment.simple()) {
    val mlflowExceptionsMap: Map[String, OFormat[_ <: MLFlowException]] = mlflowExceptions.map(x => x._1 -> x._2).toMap

    override def serialize(exception: Throwable, accept: immutable.Seq[MessageProtocol]): RawExceptionMessage = {
      exception match {
        case e: ResourceAlreadyExistsException =>
          val messageBytes =
            ByteString.fromString(
              Json.stringify(
                Json.toJson[ResourceAlreadyExistsException](e)
              ))
          RawExceptionMessage(TransportErrorCode.BadRequest, MessageProtocol(Some("application/json"), None, None), messageBytes)
        case e: ResourceDoesNotExistException =>
          val messageBytes =
            ByteString.fromString(
              Json.stringify(
                Json.toJson[ResourceDoesNotExistException](e)
              ))
          RawExceptionMessage(TransportErrorCode.NotFound, MessageProtocol(Some("application/json"), None, None), messageBytes)
        case _ =>
          super.serialize(exception, accept)
      }
    }

    override def deserialize(message: RawExceptionMessage): Throwable = {
      val excpetion = try {
        val jsValue = Json.parse(message.messageAsText)
        val exception = (jsValue \ "error_code").asOpt[JsString]
          .flatMap { errorCode =>
            val exception = mlflowExceptionsMap
              .get(errorCode.value)
              .flatMap(_.reads(jsValue).asOpt)

            exception
          }
          .map(_.asInstanceOf[RuntimeException])
          .getOrElse(super.deserialize(message))

        exception
      } catch {
        case NonFatal(ex) =>
          super.deserialize(message)
      }

      excpetion
    }
  }


  trait MLFlowException extends RuntimeException {
    def errorCode: String

    def message: String
  }

}