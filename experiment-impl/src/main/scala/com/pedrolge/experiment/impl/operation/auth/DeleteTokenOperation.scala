package com.pedrolge.experiment.impl.operation.auth

import akka.Done
import com.pedrolge.experiment.impl.repo.ApiTokenRepository
import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

class DeleteTokenOperation(apiTokenRepository: ApiTokenRepository) {
  val logger: Logger = Logger(classOf[DeleteTokenOperation])

  def process(userId: String, tokenName: String)(implicit context: ExecutionContext): Future[Done] = {

    for {
      _ <- apiTokenRepository
        .delete(userId, tokenName)
        .map{_ =>
          logger.info(s"delete token = ${tokenName}")
          Done
        }
    } yield {
      Done
    }
  }
}
