package com.pedrolge.experiment.impl.auth

import com.lightbend.lagom.scaladsl.api.transport.Forbidden
import com.lightbend.lagom.scaladsl.server.ServerServiceCall
import com.pedrolge.experiment.impl.repo.UserRepository
import com.pedrolge.experiment.impl.repo.UserRepository.User
import com.softwaremill.macwire.wire
import com.typesafe.scalalogging.Logger
import net.minidev.json.JSONObject
import org.pac4j.core.authorization.authorizer.Authorizer
import org.pac4j.core.config.Config
import org.pac4j.core.context.HttpConstants
import org.pac4j.core.profile.{AnonymousProfile, CommonProfile}
import org.pac4j.http.client.direct.HeaderClient
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator
import org.pac4j.lagom.jwt.JwtAuthenticatorHelper
import org.pac4j.lagom.scaladsl.{LagomWebContext, SecuredService}
import org.pac4j.lagom.scaladsl.transport.Unauthorized
import play.api.libs.json.{JsValue, Json}

import java.util.Collections.singletonList
import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext

trait AuthComponent extends SecuredService {

  def logger: Logger

  def authConfig: AuthConfig

  def userRepository: UserRepository

  implicit val ec: ExecutionContext

  def persistProfile[Request, Response](serviceCall: CommonProfile => ServerServiceCall[Request, Response]): CommonProfile => ServerServiceCall[Request, Response] = {
    (profile: CommonProfile) => {
      if (!profile.isInstanceOf[AnonymousProfile]) {
        ServerServiceCall.composeAsync {
          _ =>
            userRepository
              .upsert(User.getUserFromProfile(profile))
              .map { _ =>
                logger.debug(s"Upserted new user on db: ${profile.getId}")
                serviceCall(profile)
              }
        }
      } else
        serviceCall(profile)
    }
  }

  def fallbackAuthentication[Request, Response](serviceCall: CommonProfile => ServerServiceCall[Request, Response],
                                                callback: (String, CommonProfile => ServerServiceCall[Request, Response]) =>
                                                  ServerServiceCall[Request, Response]): CommonProfile => ServerServiceCall[Request, Response] = {
    (profile: CommonProfile) => {
      if (profile.isInstanceOf[AnonymousProfile] && authConfig.fallbackClient.isDefined) {
        callback(authConfig.fallbackClient.get, serviceCall)
      }
      else
        persistProfile(serviceCall)(profile)
    }
  }

  override def authenticate[Request, Response](serviceCall: CommonProfile => ServerServiceCall[Request, Response]): ServerServiceCall[Request, Response] = {
    val fallback = fallbackAuthentication(serviceCall,
      (clientName, serviceCall: CommonProfile => ServerServiceCall[Request, Response]) =>
        authenticate(clientName, serviceCall))
    authenticate(securityConfig.getClients.getDefaultSecurityClients, fallback)

  }


  override def authorize[Request, Response](authorizer: Authorizer[CommonProfile], serviceCall: CommonProfile => ServerServiceCall[Request, Response]): ServerServiceCall[Request, Response] = {
    authorize(securityConfig.getClients.getDefaultSecurityClients, authorizer, serviceCall)
  }

  override def authorize[Request, Response](
                                    clientName: String, authorizer: Authorizer[CommonProfile], serviceCall: CommonProfile => ServerServiceCall[Request, Response]): ServerServiceCall[Request, Response] =
    authenticate(clientName, (profile: CommonProfile) => ServerServiceCall.compose { requestHeader =>
      val authorized = try {
        authorizer != null && authorizer.isAuthorized(new LagomWebContext(requestHeader), singletonList(profile))
      } catch {
        case _: Exception =>
          false
      }
      if (profile == null || profile.isInstanceOf[AnonymousProfile]) {
        if (authConfig.fallbackClient.isEmpty || authConfig.fallbackClient.contains(clientName))
          throw Unauthorized("Unauthorized")
        else {
          val fallback = (clientName: String, serviceCall: CommonProfile => ServerServiceCall[Request, Response]) =>
          authorize(clientName, authorizer, serviceCall)
          fallbackAuthentication(serviceCall, fallback).apply(profile)
        }
      } else {
        if (!authorized)
          throw Forbidden("Authorization failed")
        else if (authConfig.fallbackClient.contains(clientName))
          serviceCall(profile)
        else
          persistProfile(serviceCall)(profile)
      }
    })

  lazy val securityConfig: Config = {
    val defaultClient = getJwtClient(authConfig.jwtClientConfig.clientName)
    val fallbackClient = authConfig.fallbackClient.map(clientName =>
      getApiKeyClient(clientName)
    )

    val config = if (fallbackClient.isDefined)
      new Config(defaultClient, fallbackClient.get)
    else
      new Config(defaultClient)

    config.getClients.setDefaultSecurityClients(authConfig.defaultClient)
    config
  }


  def getApiKeyClient(name: String): HeaderClient = {
    val apiKeyAuthenticator = wire[ApiKeyAuthenticator]
    val client = new HeaderClient()
    client.setHeaderName(HttpConstants.AUTHORIZATION_HEADER)
    client.setPrefixHeader(HttpConstants.BEARER_HEADER_PREFIX)
    client.setAuthenticator(apiKeyAuthenticator)
    client.setName(name)
    client.setAuthorizationGenerator((_, profile) => {
      if (profile.containsAttribute("resource_access")) {
        profile.setRoles(Set("api-key").asJava)
      }
      profile
    })

    client
  }


  def getJwtClient(name: String): HeaderClient =  {
    val jwtAuthenticator: JwtAuthenticator = JwtAuthenticatorHelper.parse(authConfig.jwtClientConfig.authenticator)
    val client = new HeaderClient()
    client.setHeaderName(HttpConstants.AUTHORIZATION_HEADER)
    client.setPrefixHeader(HttpConstants.BEARER_HEADER_PREFIX)
    client.setAuthenticator(jwtAuthenticator)
    client.setName(name)
    client.setAuthorizationGenerator((_, profile) => {
      if (profile.containsAttribute("resource_access")) {
        val resourceAccess: JSONObject = profile.getAttribute("resource_access", classOf[JSONObject])
        val jsonResourceAccess: JsValue = Json.parse(resourceAccess.toJSONString)
        profile.addRoles((jsonResourceAccess \ authConfig.jwtClientConfig.clientId \ "roles").as[List[String]].asJava)
      }
      profile
    })

    client
  }

}
