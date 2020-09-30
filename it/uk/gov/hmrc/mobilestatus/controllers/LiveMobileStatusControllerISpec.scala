package uk.gov.hmrc.mobilestatus.controllers

import play.api.libs.json.Json
import uk.gov.hmrc.mobilestatus.domain.FeatureFlag
import uk.gov.hmrc.mobilestatus.support.BaseISpec

class LiveMobileStatusControllerISpec extends BaseISpec {

  val expectedJsonResponse: String ="""{
  "feature" : [ {
    "name" : "componentisedAccessCodes",
    "enabled" : false
  } ]
}""".stripMargin

  "GET /status" should {
    "return valid response based on config" in {

      val response = await(wsUrl("/status?journeyId=7f1b5289-5f4d-4150-93a3-ff02dda28375").get)
      println(Json.prettyPrint(response.json))
      response.status shouldBe 200
      (response.json \ "feature").as[List[FeatureFlag]].size shouldBe 1
      Json.prettyPrint(response.json) shouldBe(expectedJsonResponse)
    }

    "return 400 if no journeyId supplied" in {

      val response = await(wsUrl("/status").get)
      println(Json.prettyPrint(response.json))
      response.status shouldBe 400
    }

    "return 400 if invalid journeyId supplied" in {

      val response = await(wsUrl("/status?journeyId=invalidJourneyId").get)
      println(Json.prettyPrint(response.json))
      response.status shouldBe 400
    }
  }
}
