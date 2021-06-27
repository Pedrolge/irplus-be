package com.pedrolge.experiment.impl.repo

import com.pedrolge.experiment.impl.repo.UserRepository.users
import com.pedrolge.experiment.impl.repo.ApiTokenRepository.apiTokens
import slick.dbio.DBIO
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt


trait PostgresComponent {
  val db: Database = Database.forConfig("postgres")

  val setup = DBIO.seq(
    apiTokens.schema.dropIfExists,
    users.schema.dropIfExists,
    users.schema.create,
    apiTokens.schema.create,
  )

  def runDBSetup(): Unit = {
    users.schema.create.statements.foreach(println)
    apiTokens.schema.create.statements.foreach(println)
    Await.result(db.run(setup), 30.seconds)
  }

}
