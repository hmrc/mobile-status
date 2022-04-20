package uk.gov.hmrc.mobilestatus.support

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatest.OptionValues
import org.scalatestplus.play.WsScalaTestClient
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.ws.WSClient
import play.api.test.{DefaultAwaitTimeout, FutureAwaits}

class BaseISpec
    extends AnyWordSpecLike
    with Matchers
    with OptionValues
    with WsScalaTestClient
    with GuiceOneServerPerSuite
    with WireMockSupport
    with FutureAwaits
    with DefaultAwaitTimeout {

  override implicit lazy val app: Application = appBuilder.build()

  protected val acceptJsonHeader: (String, String) = "Accept" -> "application/vnd.hmrc.1.0+json"

  def config: Map[String, Any] =
    Map(
      "auditing.enabled"                      -> true,
      "microservice.services.auth.port"       -> wireMockPort,
      "microservice.services.datastream.port" -> wireMockPort,
      "auditing.consumer.baseUri.port"        -> wireMockPort,
      "feature.componentisedAccessCodes"      -> false,
      "url.manageGovGatewayIdUrl"             -> "www.url1.gov.uk"
    )

  protected def appBuilder: GuiceApplicationBuilder = new GuiceApplicationBuilder().configure(config)

  protected implicit lazy val wsClient: WSClient = app.injector.instanceOf[WSClient]
}
