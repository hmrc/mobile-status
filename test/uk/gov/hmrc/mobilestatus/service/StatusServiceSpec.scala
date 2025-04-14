/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.mobilestatus.service

import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import uk.gov.hmrc.mobilestatus.BaseSpec
import uk.gov.hmrc.mobilestatus.config.FullScreenMessageConfigJson
import uk.gov.hmrc.mobilestatus.domain.{FeatureFlag, StatusResponse, Urls}

class StatusServiceSpec extends BaseSpec {

  override implicit lazy val app: Application = GuiceApplicationBuilder()
    .configure("nameOfConfigFile" -> "full_screen_message_config_for_test")
    .configure(
      "url.manageGovGatewayIdUrl" -> "http://localhost:8264/mobile-manage-government-gateway-id-frontend/sign-in"
    )
    .build()

  val mockFullScreenMessageConfig: FullScreenMessageConfigJson = mock[FullScreenMessageConfigJson]

  val service = new StatusService(mockFullScreenMessageConfig,
                                  "http://localhost:8264/mobile-manage-sign-in-details-frontend/sign-in",
                                  userPanelSignUp                         = false,
                                  enablePushNotificationTokenRegistration = false,
                                  paperlessAlertDialogs                   = false,
                                  paperlessAdverts                        = false,
                                  htsAdverts                              = false,
                                  customerSatisfactionSurveys             = false,
                                  findMyNinoAddToWallet                   = false,
                                  disableYourEmploymentIncomeChart        = true,
                                  disableYourEmploymentIncomeChartAndroid = true,
                                  disableYourEmploymentIncomeChartIos     = true,
                                  findMyNinoAddToGoogleWallet             = false,
                                  useLegacyWebViewForIv                   = false,
                                  appAuthThrottle                         = appAuthThrottle)

  val expectedFeatureFlags: List[FeatureFlag] = List(
    FeatureFlag("userPanelSignUp", enabled                         = false),
    FeatureFlag("enablePushNotificationTokenRegistration", enabled = false),
    FeatureFlag("paperlessAlertDialogs", enabled                   = false),
    FeatureFlag("paperlessAdverts", enabled                        = false),
    FeatureFlag("htsAdverts", enabled                              = false),
    FeatureFlag("customerSatisfactionSurveys", enabled             = false),
    FeatureFlag("findMyNinoAddToWallet", enabled                   = false),
    FeatureFlag("disableYourEmploymentIncomeChart", enabled        = true),
    FeatureFlag("disableYourEmploymentIncomeChartAndroid", enabled = true),
    FeatureFlag("disableYourEmploymentIncomeChartIos", enabled     = true),
    FeatureFlag("findMyNinoAddToGoogleWallet", enabled             = false),
    FeatureFlag("useLegacyWebViewForIv", enabled                   = false)
  )

  val expectedUrls: Urls =
    Urls(
      "http://localhost:8264/mobile-manage-sign-in-details-frontend/sign-in"
    )

  "build response" should {
    "return valid status response object" in {
      when(mockFullScreenMessageConfig.readMessageConfigJson).thenReturn(None)
      val response = service.buildStatusResponse()
      response shouldBe StatusResponse(expectedFeatureFlags, expectedUrls, appAuthThrottle)
    }

    "return valid status response object with full screen info message" in {
      when(mockFullScreenMessageConfig.readMessageConfigJson).thenReturn(Some(fullScreenMessage))
      val response = service.buildStatusResponse()
      response shouldBe StatusResponse(expectedFeatureFlags, expectedUrls, appAuthThrottle, Some(fullScreenMessage))
    }

    "return valid status response object with full screen info message with Welsh" in {
      when(mockFullScreenMessageConfig.readMessageConfigJson).thenReturn(Some(fullScreenMessageWelsh))
      val response = service.buildStatusResponse()
      response shouldBe StatusResponse(expectedFeatureFlags,
                                       expectedUrls,
                                       appAuthThrottle,
                                       Some(fullScreenMessageWelsh))
    }
  }

}
