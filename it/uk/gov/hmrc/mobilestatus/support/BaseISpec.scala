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
      "auditing.enabled"                                -> false,
      "metrics.enabled"                                 -> false,
      "microservice.services.auth.port"                 -> wireMockPort,
      "microservice.services.datastream.port"           -> wireMockPort,
      "auditing.consumer.baseUri.port"                  -> wireMockPort,
      "url.manageGovGatewayIdUrl"                       -> "www.url1.gov.uk",
      "url.cbProofOfEntitlementUrl"                     -> "www.url2.gov.uk",
      "url.cbProofOfEntitlementUrlCy"                   -> "www.url3.gov.uk",
      "url.cbPaymentHistoryUrl"                         -> "www.url4.gov.uk",
      "url.cbPaymentHistoryUrlCy"                       -> "www.url5.gov.uk",
      "feature.userPanelSignUp"                         -> false,
      "feature.enablePushNotificationTokenRegistration"  -> false,
      "feature.paperlessAlertDialogs"                   -> false,
      "feature.paperlessAdverts"                        -> false,
      "feature.htsAdverts"                              -> false,
      "feature.customerSatisfactionSurveys"             -> false,
      "feature.findMyNinoAddToWallet"                    -> false,
      "feature.disableYourEmploymentIncomeChart"        -> true,
      "feature.disableYourEmploymentIncomeChartAndroid" -> true,
      "feature.disableYourEmploymentIncomeChartIos"     -> true,
      "feature.findMyNinoAddToGoogleWallet"              -> false,
      "clientId"                                        -> "AppClientId"
    )

  protected def appBuilder: GuiceApplicationBuilder = new GuiceApplicationBuilder().configure(config)

  protected implicit lazy val wsClient: WSClient = app.injector.instanceOf[WSClient]
}
