package uk.gov.hmrc.mobilestatus.controllers

import play.api.libs.json.Json
import uk.gov.hmrc.mobilestatus.domain.FeatureFlag
import uk.gov.hmrc.mobilestatus.support.BaseISpec

class LiveMobileStatusControllerISpec extends BaseISpec {
  override def config:      Map[String, Any] = super.config ++ Map[String, Any]("nameOfConfigFile" -> "full_screen_message_config_for_test")

  val expectedJsonResponse: String = """{
  "feature" : [ ],
  "fullScreenInfoMessage" : {
    "id" : "496dde52-4912-4af2-8b3c-33c6f8afedf9",
    "type" : "Info",
    "content" : {
      "title" : "Some title",
      "body" : "Some body"
    },
    "links" : [ {
      "url" : "https://www.abc.com",
      "urlType" : "Normal",
      "type" : "Secondary",
      "message" : "Title 1"
    }, {
      "urlType" : "Dismiss",
      "type" : "Primary",
      "message" : "Title 2"
    } ]
  }
}""".stripMargin

  "GET /status" should {
    "return valid response based on config" in {

      val response = await(wsUrl("/mobile-status/status?journeyId=7f1b5289-5f4d-4150-93a3-ff02dda28375").get)
      println(Json.prettyPrint(response.json))
      response.status                                        shouldBe 200
      (response.json \ "feature").as[List[FeatureFlag]].size shouldBe 0
      Json.prettyPrint(response.json)                        shouldBe (expectedJsonResponse)
    }

    "return 400 if no journeyId supplied" in {

      val response = await(wsUrl("/mobile-status/status").get)
      println(Json.prettyPrint(response.json))
      response.status shouldBe 400
    }

    "return 400 if invalid journeyId supplied" in {

      val response = await(wsUrl("/mobile-status/status?journeyId=invalidJourneyId").get)
      println(Json.prettyPrint(response.json))
      response.status shouldBe 400
    }
  }
}

class MobileStatusInvalidFileNameFullScreenMessageISpec extends BaseISpec {

  val expectedJsonResponse: String           = """{
  "feature" : [ ]
}""".stripMargin
  override def config:      Map[String, Any] = super.config ++ Map[String, Any]("nameOfConfigFile" -> "INVALID_NAME")

  s"GET /status" should {
    "return valid response without a fullScreenInfoMessage" in {
      val response = await(wsUrl("/mobile-status/status?journeyId=7f1b5289-5f4d-4150-93a3-ff02dda28375").get)
      response.status                                        shouldBe 200
      (response.json \ "feature").as[List[FeatureFlag]].size shouldBe 0
      Json.prettyPrint(response.json)                        shouldBe (expectedJsonResponse)
    }
  }
}

class MobileStatusAppShutteredFullScreenMessageISpec extends BaseISpec {

  val expectedJsonResponse: String = """{
  "feature" : [ ],
  "fullScreenInfoMessage" : {
    "id" : "496dde52-4912-4af2-8b3c-33c6f8afedf9",
    "type" : "Info",
    "content" : {
      "title" : "Some title",
      "body" : "Some body"
    }
}""".stripMargin

  override def config: Map[String, Any] =
    super.config ++ Map[String, Any]("shuttering.appShuttered" -> true,
                                     "shuttering.title"        -> "QXBwIFVuYXZhaWxhYmxl",
                                     "shuttering.message"      -> "UGxlYXNlIHRyeSBhZ2FpbiBsYXRlci4=")

  s"GET /status" should {
    "return valid response without a fullScreenInfoMessage" in {
      val response = await(wsUrl("/mobile-status/status?journeyId=7f1b5289-5f4d-4150-93a3-ff02dda28375").get)
      response.status                                                            shouldBe 200
      (response.json \ "feature").as[List[FeatureFlag]].size                     shouldBe 0
      (response.json \ "fullScreenInfoMessage" \ "type").as[String]              shouldBe "Shutter"
      (response.json \ "fullScreenInfoMessage" \ "content" \ "title").as[String] shouldBe "App Unavailable"
      (response.json \ "fullScreenInfoMessage" \ "content" \ "body").as[String]  shouldBe "Please try again later."
    }
  }
}
