package com.pedrolge.experiment.api

import play.api.libs.json.{Json, OWrites, Reads}

package object model {

  case class EmptyResponse()

  object EmptyResponse {
    implicit val nonStrictReads: Reads[EmptyResponse] = Reads.pure(EmptyResponse())
    implicit val writes: OWrites[EmptyResponse] = OWrites[EmptyResponse](_ => Json.obj())
  }

}
