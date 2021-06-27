package com.pedrolge.experiment.impl.repo

import com.pedrolge.experiment.impl.repo.ApiTokenRepository.apiTokens
import com.pedrolge.experiment.impl.repo.UserRepository.{User, users}
import org.pac4j.core.profile.CommonProfile
import org.pac4j.core.util.JavaSerializationHelper
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import scala.concurrent.{ExecutionContext, Future}


object UserRepository {

  val javaSerializationHelper = new JavaSerializationHelper

  case class User(id: String, serializedProfile: String) {
    def getProfile: CommonProfile = javaSerializationHelper.unserializeFromBase64(serializedProfile).asInstanceOf[CommonProfile]
  }

  object User {
    def getUserFromProfile(profile: CommonProfile): User = {
      User(profile.getId, javaSerializationHelper.serializeToBase64(profile))
    }
  }

  class Users(tag: Tag) extends Table[User](tag, "USERS") {
    def id = column[String]("ID", O.PrimaryKey)

    def serializedProfile = column[String]("SERIALIZED_PROFILE")

    def * = (id, serializedProfile).shaped <> ( {
      case (id, serializedProfile) =>
        User(id, serializedProfile)
    }, { u: User =>
      Some(u.id, u.serializedProfile)
    }
    )
  }

  val users = TableQuery[Users]
}

class UserRepository(val db: Database) extends Repository[User] {

  def create(user: User)(implicit context: ExecutionContext): Future[Int] = {
    val q =
      users += user

    db.run(q)
  }

  def read(id: String)(implicit context: ExecutionContext): Future[Option[User]] = {
    val q =
      users.filter(_.id === id)

    db.run(q.result)
      .map(_.headOption)
  }

  def readByToken(tokenHash: String)(implicit context: ExecutionContext): Future[Option[User]] = {
    val q = for {
      token <- apiTokens
      user <- users if token.userId === user.id && token.token === tokenHash
    } yield {
      user
    }

    db.run(q.result)
      .map(_.headOption)
  }


  def upsert(user: User)(implicit context: ExecutionContext): Future[Int] = {
    val q =
      users.insertOrUpdate(user)

    db.run(q)
  }

  def delete(id: String)(implicit context: ExecutionContext): Future[Int] = {
    val q =
      users
        .filter(_.id === id)
        .delete

    db.run(q)
  }

}