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

import org.mockito.Mockito.when
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import uk.gov.hmrc.mobilestatus.BaseSpec
import uk.gov.hmrc.mobilestatus.config.FullScreenMessageConfigJson
import uk.gov.hmrc.mobilestatus.domain.{FeatureFlag, StatusResponse, Urls}

class StatusServiceSpec extends BaseSpec {

  override implicit lazy val app: Application = GuiceApplicationBuilder()
    .configure("nameOfConfigFile" -> "full_screen_message_config_for_test")
    .configure(
      "url.manageGovGatewayIdUrl"   -> "http://localhost:8264/mobile-manage-government-gateway-id-frontend/sign-in",
      "url.cbProofOfEntitlementUrl"   -> "/child-benefit/view-proof-entitlement",
      "url.cbProofOfEntitlementUrlCy" -> "/child-benefit/view-proof-entitlement",
      "url.cbPaymentHistoryUrl"       -> "/child-benefit/view-payment-history",
      "url.cbPaymentHistoryUrlCy"     -> "/child-benefit/view-payment-history"
    )
    .build()

  val mockFullScreenMessageConfig: FullScreenMessageConfigJson = mock[FullScreenMessageConfigJson]

  val service = new StatusService("http://localhost:8264/mobile-manage-government-gateway-id-frontend/sign-in",
                                  "/child-benefit/view-proof-entitlement",
                                  "/child-benefit/view-proof-entitlement",
                                  "/child-benefit/view-payment-history",
                                  "/child-benefit/view-payment-history",
                                  mockFullScreenMessageConfig)

  val expectedFeatureFlags = List.empty

  val expectedUrls =
    Urls(
      "http://localhost:8264/mobile-manage-government-gateway-id-frontend/sign-in",
      "/child-benefit/view-proof-entitlement",
      "/child-benefit/view-proof-entitlement",
      "/child-benefit/view-payment-history",
      "/child-benefit/view-payment-history"
    )

  "build response" should {
    "return valid status response object" in {
      when(mockFullScreenMessageConfig.readMessageConfigJson).thenReturn(None)
      val response = service.buildStatusResponse()
      response shouldBe StatusResponse(expectedFeatureFlags, expectedUrls)
    }

    "return valid status response object with full screen info message" in {
      when(mockFullScreenMessageConfig.readMessageConfigJson).thenReturn(Some(fullScreenMessage))
      val response = service.buildStatusResponse()
      response shouldBe StatusResponse(expectedFeatureFlags, expectedUrls, Some(fullScreenMessage))
    }
  }

}
