package com.pedrolge.experiment.api.model

import com.pedrolge.mlflow.api.model.MLFlowFormatter
import play.api.libs.json.{Json, OFormat}

package object auth {

  case class ApiKey(tokenName: String, prefix: String, token: String)

  object ApiKey extends MLFlowFormatter {
    implicit val format: OFormat[ApiKey] = Json.format
  }

  case class ApiKeyPrefix(tokenName: String, prefix: String)

  object ApiKeyPrefix extends MLFlowFormatter {
    implicit val format: OFormat[ApiKeyPrefix] = Json.format
  }

  case class CreateTokenRequest(tokenName: String)

  object CreateTokenRequest extends MLFlowFormatter {
    implicit val format: OFormat[CreateTokenRequest] = Json.format
  }

  case class CreateTokenResponse(apiKey: ApiKey)

  object CreateTokenResponse extends MLFlowFormatter {
    implicit val format: OFormat[CreateTokenResponse] = Json.format
  }

  case class ListTokenResponse(apiKeys: List[ApiKeyPrefix])

  object ListTokenResponse extends MLFlowFormatter {
    implicit val format: OFormat[ListTokenResponse] = Json.format
  }

}
