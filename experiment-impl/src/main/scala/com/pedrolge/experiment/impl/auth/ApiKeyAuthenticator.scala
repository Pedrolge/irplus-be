package com.pedrolge.experiment.impl.auth

import com.pedrolge.experiment.api.model.auth.ApiKey
import com.pedrolge.experiment.impl.repo.ApiTokenRepository.ApiToken
import com.pedrolge.experiment.impl.repo.UserRepository
import com.pedrolge.experiment.impl.repo.UserRepository.User
import org.pac4j.core.context.WebContext
import org.pac4j.core.credentials.TokenCredentials
import org.pac4j.core.credentials.authenticator.Authenticator
import org.pac4j.core.profile.CommonProfile
import org.pac4j.core.profile.definition.{CommonProfileDefinition, ProfileDefinitionAware}

import java.util.concurrent.TimeoutException
import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.DurationInt

class ApiKeyAuthenticator(userRepository: UserRepository)(implicit ec: ExecutionContext) extends ProfileDefinitionAware[CommonProfile] with Authenticator[TokenCredentials]{
  override def internalInit(): Unit = {
    defaultProfileDefinition(new CommonProfileDefinition());
  }

  override def validate(credentials: TokenCredentials, context: WebContext): Unit = {

    val future = for {
      user <- userRepository
      .readByToken(ApiToken.hash(credentials.getToken))

      profile = user.map(_.getProfile)
    } yield {
      profile
    }

    val result =
      try {
        Await.result(future, 10.seconds)
      } catch {
        case _: TimeoutException =>
          None
      }

    if (result.isDefined)
      credentials.setUserProfile(result.get)
  }

}
