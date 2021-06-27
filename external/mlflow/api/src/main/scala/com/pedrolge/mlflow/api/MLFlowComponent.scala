package com.pedrolge.mlflow.api

import com.lightbend.lagom.scaladsl.client.{StandaloneLagomClientFactory, StaticServiceLocatorComponents}
import com.typesafe.config.Config
import play.api.libs.ws.ahc.AhcWSComponents

import java.net.URI

trait MLFlowComponent {

  def config: Config

  lazy val mlFlowConfig: MLFlowConfig = MLFlowConfig.readConfig

  val factory: StandaloneLagomClientFactory =
    new StandaloneLagomClientFactory(mlFlowConfig.clientName, classOf[StandaloneLagomClientFactory].getClassLoader)
      with StaticServiceLocatorComponents
      with AhcWSComponents {

      override def staticServiceUri: URI = URI.create(mlFlowConfig.URI)

    }

  lazy val mlflowService: MLFlow = factory.serviceClient.implement[MLFlow]

}
