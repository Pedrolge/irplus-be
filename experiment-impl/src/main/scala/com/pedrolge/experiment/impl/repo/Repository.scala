package com.pedrolge.experiment.impl.repo

import slick.jdbc.PostgresProfile.api._

trait Repository[Entity] {

  val db: Database

}
