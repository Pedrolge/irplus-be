package com.pedrolge.experiment.impl.auth

import net.minidev.json.JSONObject
import org.pac4j.core.config.Config
import org.pac4j.core.context.HttpConstants
import org.pac4j.http.client.direct.HeaderClient
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator
import org.pac4j.lagom.jwt.JwtAuthenticatorHelper
import play.api.libs.json.{JsValue, Json}

import scala.collection.JavaConverters._

trait AuthComponent {

  def authConfig: AuthConfig

  def getSecurityConfig: Config = {
    val jwtAuthenticator: JwtAuthenticator = JwtAuthenticatorHelper.parse(authConfig.authenticator)

    lazy val client: HeaderClient = {
      val client = new HeaderClient()
      client.setHeaderName(HttpConstants.AUTHORIZATION_HEADER)
      client.setPrefixHeader(HttpConstants.BEARER_HEADER_PREFIX)
      client.setAuthenticator(jwtAuthenticator)


      client.setAuthorizationGenerator((_, profile) => {
        if (profile.containsAttribute("resource_access")) {
          val resourceAccess: JSONObject = profile.getAttribute("resource_access", classOf[JSONObject])
          val jsonResourceAccess: JsValue = Json.parse(resourceAccess.toJSONString)
          profile.addRoles((jsonResourceAccess \ authConfig.keycloak.clientId \ "roles").as[List[String]].asJava)
        }
        profile
      })

      client.setName(authConfig.keycloak.clientName)
      client
    }

    val config = new Config(client)
    config.getClients.setDefaultSecurityClients(client.getName)
    config
  }




}
