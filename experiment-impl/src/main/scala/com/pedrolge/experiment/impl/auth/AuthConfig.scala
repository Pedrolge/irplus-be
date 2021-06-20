package com.pedrolge.experiment.impl.auth

import com.typesafe.config.Config
import pureconfig._
import pureconfig.generic.auto._


case class AuthConfig(
                       defaultClient: String,
                       fallbackClient: Option[String],
                       jwtClientConfig: JwtClientConfig,
                     )

case class JwtClientConfig(
                            clientName: String,
                            clientId: String,
                            authenticator: Config
                          )


object AuthConfig {
  def readConfig: AuthConfig =
    ConfigSource.default.at("auth").load[AuthConfig]
      .getOrElse(throw new RuntimeException("Could not load auth config"))
}