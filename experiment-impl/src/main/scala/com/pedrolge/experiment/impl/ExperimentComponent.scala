package com.pedrolge.experiment.impl

import com.lightbend.lagom.scaladsl.api.LagomConfigComponent
import com.pedrolge.experiment.impl.operation.experiment._
import com.pedrolge.experiment.impl.operation.run._
import com.pedrolge.experiment.impl.operation.metric._
import com.pedrolge.experiment.impl.operation.artifact._
import com.pedrolge.experiment.impl.operation.modelVersion._
import com.pedrolge.experiment.impl.operation.registeredModel._

import com.softwaremill.macwire.wire

trait ExperimentComponent
  extends LagomConfigComponent
    with OperationRegistry {

  final lazy val listExperimentsOperation = wire[ListExperimentsOperation]
  final lazy val createExperimentOperation = wire[CreateExperimentOperation]
  final lazy val getExperimentOperation = wire[GetExperimentOperation]
  final lazy val getExperimentByNameOperation = wire[GetExperimentByNameOperation]
  final lazy val deleteExperimentOperation = wire[DeleteExperimentOperation]
  final lazy val restoreExperimentOperation = wire[RestoreExperimentOperation]
  final lazy val updateExperimentOperation = wire[UpdateExperimentOperation]

  final lazy val setExperimentTagOperation = wire[SetExperimentTagOperation]
  final lazy val createRunOperation = wire[CreateRunOperation]
  final lazy val deleteRunOperation = wire[DeleteRunOperation]
  final lazy val restoreRunOperation = wire[RestoreRunOperation]
  final lazy val getRunOperation = wire[GetRunOperation]
  final lazy val logMetricOperation = wire[LogMetricOperation]
  final lazy val logBatchOperation = wire[LogBatchOperation]
  final lazy val logModelOperation = wire[LogModelOperation]
  final lazy val setTagOperation = wire[SetTagOperation]
  final lazy val deleteTagOperation = wire[DeleteTagOperation]
  final lazy val logParameterOperation = wire[LogParameterOperation]
  final lazy val searchRunOperation = wire[SearchRunOperation]
  final lazy val updateRunOperation = wire[UpdateRunOperation]

  final lazy val getMetricHistoryOperation = wire[GetMetricHistoryOperation]

  final lazy val listArtifactsOperation = wire[ListArtifactsOperation]

  final lazy val createRegisteredModelOperation = wire[CreateRegisteredModelOperation]
  final lazy val getRegisteredModelOperation = wire[GetRegisteredModelOperation]
  final lazy val renameRegisteredModelOperation = wire[RenameRegisteredModelOperation]
  final lazy val updateRegisteredModelOperation = wire[UpdateRegisteredModelOperation]
  final lazy val deleteRegisteredModelOperation = wire[DeleteRegisteredModelOperation]
  final lazy val listRegisteredModelOperation = wire[ListRegisteredModelOperation]
  final lazy val getLatestVersionsOperation = wire[GetLatestVersionsOperation]
  final lazy val searchRegisteredModelOperation = wire[SearchRegisteredModelOperation]
  final lazy val setRegisteredModelTagOperation = wire[SetRegisteredModelTagOperation]
  final lazy val deleteRegisteredModelTagOperation = wire[DeleteRegisteredModelTagOperation]

  final lazy val createModelVersionOperation = wire[CreateModelVersionOperation]
  final lazy val getModelVersionOperation = wire[GetModelVersionOperation]
  final lazy val updateModelVersionOperation = wire[UpdateModelVersionOperation]
  final lazy val deleteModelVersionOperation = wire[DeleteModelVersionOperation]
  final lazy val searchModelVersionOperation = wire[SearchModelVersionOperation]
  final lazy val getModelVersionDownloadUriOperation = wire[GetModelVersionDownloadUriOperation]
  final lazy val transitionModelVersionStageOperation = wire[TransitionModelVersionStageOperation]
  final lazy val setModelVersionTagOperation = wire[SetModelVersionTagOperation]
  final lazy val deleteModelVersionTagOperation = wire[DeleteModelVersionTagOperation]


  def operationRegistry: OperationRegistry
}
