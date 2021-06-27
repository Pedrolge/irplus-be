package com.pedrolge.mlflow.api

import com.typesafe.config.Config
import pureconfig._
import pureconfig.generic.auto._


case class MLFlowConfig(
                     clientName: String,
                     URI: String
                     )


object MLFlowConfig {
  def readConfig: MLFlowConfig =
    ConfigSource.default.at("mlflow").load[MLFlowConfig]
      .getOrElse(throw new RuntimeException("Could not load auth config"))
}