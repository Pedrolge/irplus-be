package com.pedrolge.experiment.impl.auth

import com.typesafe.config.Config
import pureconfig._
import pureconfig.generic.auto._


case class AuthConfig(
                     keycloak: KeycloakConfig,
                     authenticator: Config
                     )


case class KeycloakConfig(
                           clientId: String,
                           clientName: String
                         )


object AuthConfig {
  def readConfig: AuthConfig =
    ConfigSource.default.at("auth").load[AuthConfig]
      .getOrElse(throw new RuntimeException("Could not load auth config"))
}