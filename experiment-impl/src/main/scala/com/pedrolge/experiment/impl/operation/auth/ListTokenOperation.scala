package com.pedrolge.experiment.impl.operation.auth

import akka.Done
import com.pedrolge.experiment.api.model.auth.{CreateTokenRequest, CreateTokenResponse, ListTokenResponse}
import com.pedrolge.experiment.impl.repo.ApiTokenRepository
import com.pedrolge.experiment.impl.repo.ApiTokenRepository.ApiToken
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class ListTokenOperation(apiTokenRepository: ApiTokenRepository) {
  val logger: Logger = Logger(classOf[ListTokenOperation])

  def process(userId: String)(implicit context: ExecutionContext): Future[ListTokenResponse] = {

    for {
      result <- apiTokenRepository
        .readByUserId(userId)
        .map{result =>
          logger.info(s"create token = ${result.toString}")
          result
        }
    } yield {
      ListTokenResponse(result.map(_.toResponse))
    }
  }
}
