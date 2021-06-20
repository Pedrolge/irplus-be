package com.pedrolge.experiment.impl.repo

import com.pedrolge.experiment.api.model.auth.{ApiKey, ApiKeyPrefix, CreateTokenResponse}
import com.pedrolge.experiment.impl.repo.ApiTokenRepository.{ApiToken, apiTokens}
import com.pedrolge.experiment.impl.repo.UserRepository.users
import com.roundeights.hasher.Implicits._
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random


object ApiTokenRepository {

  case class ApiToken(userId: String, name: String, prefix: String, tokenHash: String) {

    def toResponse: ApiKeyPrefix = {
      ApiKeyPrefix(
        tokenName = this.name,
        prefix = this.prefix
      )
    }
  }

  object ApiToken {
    def apply(userId: String, name: String, token: String): ApiToken = {
      ApiToken(userId, name, ApiToken.prefix(token), ApiToken.hash(token))
    }

    val r = new Random()

    def generateToken: String =
      r.alphanumeric.take(7).mkString + "." + r.alphanumeric.take(32).mkString

    def hash(s: String): String = s.sha256

    def prefix(s: String, length: Int = 7): String = s.take(length)
  }

  class ApiTokens(tag: Tag) extends Table[ApiToken](tag, "API_TOKEN") {
    def userId = column[String]("USER_ID")

    def name = column[String]("NAME")

    def prefix = column[String]("PREFIX")

    def token = column[String]("TOKEN")

    def user = foreignKey("USER_FK", userId, users)(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)

    def uniqueIdx = index("user_id_name_unique", (userId, name), unique = true)

    def * = (userId, name, prefix, token).shaped <> ( {
      case (userId, name, prefix, token) =>
        ApiToken(userId, name, prefix, token)
    }, { at: ApiToken =>
      Some((at.userId, at.name, at.prefix, at.tokenHash))
    }
    )
  }

  val apiTokens = TableQuery[ApiTokens]
}


class ApiTokenRepository(val db: Database) extends Repository[ApiToken] {

  def create(token: ApiToken)(implicit context: ExecutionContext): Future[Int] = {
    val q =
      apiTokens += token

    db.run(q)
  }

  def readByUserId(userId: String)(implicit context: ExecutionContext): Future[List[ApiToken]] = {
    val q =
      apiTokens.filter(_.userId === userId)

    db.run(q.result).map(_.toList)
  }

  def readByToken(tokenHash: String)(implicit context: ExecutionContext): Future[Option[ApiToken]] = {
    val q =
      apiTokens.filter(_.token === tokenHash)

    db.run(q.result).map(_.headOption)
  }

  def upsert(token: ApiToken)(implicit context: ExecutionContext): Future[Int] = {
    val q =
      apiTokens.insertOrUpdate(token)

    db.run(q)
  }

  def delete(userId: String)(implicit context: ExecutionContext): Future[Int] = {
    val q =
      apiTokens
        .filter(_.userId === userId)
        .delete

    db.run(q)
  }

}