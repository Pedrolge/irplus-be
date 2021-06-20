package com.pedrolge.experiment.impl

import com.pedrolge.experiment.impl.operation.experiment._
import com.pedrolge.experiment.impl.operation.run._
import com.pedrolge.experiment.impl.operation.metric._
import com.pedrolge.experiment.impl.operation.artifact._
import com.pedrolge.experiment.impl.operation.auth.{CreateTokenOperation, ListTokenOperation}
import com.pedrolge.experiment.impl.operation.registeredModel._
import com.pedrolge.experiment.impl.operation.modelVersion._
import com.pedrolge.experiment.impl.repo.{ApiTokenRepository, UserRepository}
import com.pedrolge.mlflow.api.MLFlow

trait OperationRegistry {

  def createTokenOperation: CreateTokenOperation
  def listTokenOperation: ListTokenOperation

  def listExperimentsOperation: ListExperimentsOperation
  def createExperimentOperation: CreateExperimentOperation
  def getExperimentOperation: GetExperimentOperation
  def getExperimentByNameOperation: GetExperimentByNameOperation
  def deleteExperimentOperation: DeleteExperimentOperation
  def restoreExperimentOperation: RestoreExperimentOperation
  def updateExperimentOperation: UpdateExperimentOperation
  def setExperimentTagOperation: SetExperimentTagOperation

  def createRunOperation: CreateRunOperation
  def deleteRunOperation: DeleteRunOperation
  def restoreRunOperation: RestoreRunOperation
  def getRunOperation: GetRunOperation
  def logMetricOperation: LogMetricOperation
  def logBatchOperation: LogBatchOperation
  def logModelOperation: LogModelOperation
  def setTagOperation: SetTagOperation
  def deleteTagOperation: DeleteTagOperation
  def logParameterOperation: LogParameterOperation
  def searchRunOperation: SearchRunOperation
  def updateRunOperation: UpdateRunOperation

  def getMetricHistoryOperation: GetMetricHistoryOperation

  def listArtifactsOperation: ListArtifactsOperation

  def createRegisteredModelOperation: CreateRegisteredModelOperation
  def getRegisteredModelOperation: GetRegisteredModelOperation
  def renameRegisteredModelOperation: RenameRegisteredModelOperation
  def updateRegisteredModelOperation: UpdateRegisteredModelOperation
  def deleteRegisteredModelOperation: DeleteRegisteredModelOperation
  def listRegisteredModelOperation: ListRegisteredModelOperation
  def getLatestVersionsOperation: GetLatestVersionsOperation
  def searchRegisteredModelOperation: SearchRegisteredModelOperation
  def setRegisteredModelTagOperation: SetRegisteredModelTagOperation
  def deleteRegisteredModelTagOperation: DeleteRegisteredModelTagOperation
  def createModelVersionOperation: CreateModelVersionOperation
  def getModelVersionOperation: GetModelVersionOperation
  def updateModelVersionOperation: UpdateModelVersionOperation
  def deleteModelVersionOperation: DeleteModelVersionOperation
  def searchModelVersionOperation: SearchModelVersionOperation
  def getModelVersionDownloadUriOperation: GetModelVersionDownloadUriOperation
  def transitionModelVersionStageOperation: TransitionModelVersionStageOperation
  def setModelVersionTagOperation: SetModelVersionTagOperation
  def deleteModelVersionTagOperation: DeleteModelVersionTagOperation

  def mlflowService: MLFlow
  def userRepository: UserRepository
  def apiTokenRepository: ApiTokenRepository

  def operationRegistry: OperationRegistry = this
}
