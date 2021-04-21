/*
 * Copyright 2021 HM Revenue & Customs
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
import uk.gov.hmrc.mobilestatus.domain.{Content, FeatureFlag, FullScreenInfoMessage, StatusResponse}

class StatusServiceSpec extends BaseSpec {
  override implicit lazy val app: Application = GuiceApplicationBuilder()
    .configure("nameOfConfigFile" -> "full_screen_message_config_for_test")
    .build()

  val mockFullScreenMessageConfig = mock[FullScreenMessageConfigJson]

  val service = new StatusService(componentisedAccessCodes = false, mockFullScreenMessageConfig)

  val expectedFeatureFlags = List(FeatureFlag("componentisedAccessCodes", enabled = false))

  "build response" should {
    "return valid status response object" in {
      when(mockFullScreenMessageConfig.readMessageConfigJson).thenReturn(None)
      val response = service.buildStatusResponse()
      response shouldBe StatusResponse(expectedFeatureFlags)
    }

    "return valid status response object with full screen info message" in {
      when(mockFullScreenMessageConfig.readMessageConfigJson).thenReturn(Some(fullScreenMessage))
      val response = service.buildStatusResponse()
      response shouldBe StatusResponse(expectedFeatureFlags, Some(fullScreenMessage))
    }
  }

}
