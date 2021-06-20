package com.pedrolge.experiment.impl.operation.auth

import akka.Done
import com.pedrolge.experiment.api.model.auth.{ApiKey, CreateTokenRequest, CreateTokenResponse}
import com.pedrolge.experiment.impl.repo.ApiTokenRepository
import com.pedrolge.experiment.impl.repo.ApiTokenRepository.ApiToken
import com.typesafe.scalalogging.Logger
import org.postgresql.util.PSQLException

import scala.concurrent.{ExecutionContext, Future}

class CreateTokenOperation(apiTokenRepository: ApiTokenRepository) {
  val logger: Logger = Logger(classOf[CreateTokenOperation])

  def process(userId: String, request: CreateTokenRequest)(implicit context: ExecutionContext): Future[CreateTokenResponse] = {

    val token = ApiToken.generateToken
    val apiToken = ApiToken(userId, request.tokenName, token)

    for {
      _ <- apiTokenRepository
        .create(apiToken)
        .map{_ =>
          logger.info(s"create token = ${apiToken.toString}")
          Done
        }
    } yield {
      CreateTokenResponse(ApiKey(request.tokenName, token))
    }
  }
}
