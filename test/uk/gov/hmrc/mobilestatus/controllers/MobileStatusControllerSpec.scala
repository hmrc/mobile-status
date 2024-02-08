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

package uk.gov.hmrc.mobilestatus.controllers

import play.api.test.Helpers._
import eu.timepit.refined.auto._
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.mobilestatus.BaseSpec
import uk.gov.hmrc.mobilestatus.domain.{FeatureFlag, StatusResponse, Urls}
import uk.gov.hmrc.mobilestatus.domain.types.ModelTypes.JourneyId
import uk.gov.hmrc.mobilestatus.service.StatusService

class MobileStatusControllerSpec extends BaseSpec {

  private val fakeRequest = FakeRequest("GET", "/")

  val service:   StatusService = mock[StatusService]
  val journeyId: JourneyId     = "252a1a16-831b-46bd-bf51-4e55d4d1c088"

  private val controller = new LiveMobileStatusController(Helpers.stubControllerComponents(), service)

  private val featureFlagList: List[FeatureFlag] = List(FeatureFlag("flag1", enabled = true),
                                                        FeatureFlag("flag2", enabled = true),
                                                        FeatureFlag("flag3", enabled = false))

  private val urls: Urls =
    Urls("https://url1.com","https://url2.com")

  "GET /status" should {
    "return 200 with valid correct response" in {
      when(service.buildStatusResponse())
        .thenReturn(StatusResponse(featureFlagList, urls, clientId, Some(fullScreenMessage)))
      val result = controller.status(journeyId)(fakeRequest)
      status(result)                                                                       shouldBe Status.OK
      contentAsJson(result).toString().contains(Json.toJson(featureFlagList).toString())   shouldBe true
      contentAsJson(result).toString().contains(Json.toJson(urls).toString())              shouldBe true
      contentAsJson(result).toString().contains(Json.toJson(fullScreenMessage).toString()) shouldBe true
      contentAsJson(result).toString().contains(clientId)                                  shouldBe true
    }

    "return 500 when exception is thrown" in {
      when(service.buildStatusResponse()).thenThrow(new RuntimeException)
      val result = controller.status(journeyId)(fakeRequest)
      status(result) shouldBe Status.INTERNAL_SERVER_ERROR
    }
  }
}
