package com.pedrolge.experiment.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server._
import com.pedrolge.experiment.api.ExperimentService
import com.pedrolge.experiment.impl.auth.{AuthComponent, AuthConfig}
import com.pedrolge.mlflow.api.MLFlowComponent
import com.softwaremill.macwire._
import org.pac4j.core.config.Config
import play.api.libs.ws.ahc.AhcWSComponents

import scala.concurrent.Future


class ExperimentLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new ExperimentApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new ExperimentApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[ExperimentService])
}

abstract class ExperimentApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents
    with OperationRegistry
    with ExperimentComponent
    with AuthComponent
    with MLFlowComponent {

  // configs
  val authConfig: AuthConfig = AuthConfig.readConfig
  val securityConfig: Config = this.getSecurityConfig


  // Bind the service that this server provides
  override lazy val lagomServer: LagomServer = serverFor[ExperimentService](wire[ExperimentServiceImpl])

}
