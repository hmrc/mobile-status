/*
 * Copyright 2020 HM Revenue & Customs
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

import org.scalatest.{Matchers, WordSpecLike}
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.{Configuration, Environment}
import play.api.http.Status
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}
import eu.timepit.refined.auto._
import uk.gov.hmrc.mobilestatus.domain.types.ModelTypes.JourneyId
import uk.gov.hmrc.mobilestatus.service.StatusService
import scala.concurrent.ExecutionContext.Implicits.global

class MobileStatusControllerSpec extends WordSpecLike with Matchers with GuiceOneAppPerSuite with MockitoSugar {

  private val fakeRequest = FakeRequest("GET", "/")

  private val env           = Environment.simple()
  private val configuration = Configuration.load(env)

  val service: StatusService = mock[StatusService]
  val journeyId: JourneyId = "252a1a16-831b-46bd-bf51-4e55d4d1c088"

  private val controller = new LiveMobileStatusController(Helpers.stubControllerComponents(), service)

  "GET /" should {
    "return 200" in {
      val result = controller.getAppStatus(journeyId)
      status(result) shouldBe Status.OK
    }
  }
}
