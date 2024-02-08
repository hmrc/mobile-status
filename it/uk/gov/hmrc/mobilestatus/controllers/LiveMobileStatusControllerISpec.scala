package uk.gov.hmrc.mobilestatus.controllers

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.mobilestatus.domain.{FeatureFlag, Urls}
import uk.gov.hmrc.mobilestatus.support.BaseISpec

class LiveMobileStatusControllerISpec extends BaseISpec {

  val expectedJsonResponse: JsValue = Json.parse("""{
    "feature": [
      {
        "name": "userPanelSignUp",
        "enabled": false
      },
      {
        "name": "enablePushNotificationTokenRegistration",
        "enabled": false
      },
      {
        "name": "paperlessAlertDialogs",
        "enabled": false
      },
      {
        "name": "paperlessAdverts",
        "enabled": false
      },
      {
        "name": "htsAdverts",
        "enabled": false
      },
      {
        "name": "customerSatisfactionSurveys",
        "enabled": false
      },
      {
        "name": "findMyNinoAddToWallet",
        "enabled": false
      },
      {
        "name": "disableYourEmploymentIncomeChart",
        "enabled": true
      },
      {
        "name": "disableYourEmploymentIncomeChartAndroid",
        "enabled": true
      },
      {
        "name": "disableYourEmploymentIncomeChartIos",
        "enabled": true
      },
      {
        "name": "findMyNinoAddToGoogleWallet",
        "enabled": false
      }
    ],
    "urls": {
      "manageGovGatewayIdUrl": "www.url1.gov.uk",
      "tcNationalInsuranceRatesLettersUrl": "www.url6.gov.uk"
    },
    "clientId" : "AppClientId",
    "fullScreenInfoMessage": {
      "id": "496dde52-4912-4af2-8b3c-33c6f8afedf9",
      "type": "Info",
      "content": {
        "title": "Some title",
        "body": "Some body"
      },
      "links": [
        {
          "url": "https://www.abc.com",
          "urlType": "Normal",
          "type": "Secondary",
          "message": "Title 1"
        },
        {
          "urlType": "Dismiss",
          "type": "Primary",
          "message": "Title 2"
        }
      ]
    }
  }""".stripMargin)

  override def config: Map[String, Any] =
    super.config ++ Map[String, Any]("nameOfConfigFile" -> "full_screen_message_config_for_test")

  "GET /status" should {
    "return valid response based on config" in {

      val response = await(wsUrl("/mobile-status/status?journeyId=7f1b5289-5f4d-4150-93a3-ff02dda28375").get)
      response.status                                             shouldBe 200
      (response.json \ "feature").as[List[FeatureFlag]].size      shouldBe 11
      (response.json \ "urls").as[Urls].manageGovGatewayIdUrl     shouldBe "www.url1.gov.uk"
      response.json                                               shouldBe expectedJsonResponse
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

  val expectedJsonResponse: JsValue = Json.parse("""{
  "feature" : [
      {
         "name": "userPanelSignUp",
         "enabled": false
       },
       {
         "name": "enablePushNotificationTokenRegistration",
         "enabled": false
       },
       {
         "name": "paperlessAlertDialogs",
         "enabled": false
       },
       {
         "name": "paperlessAdverts",
         "enabled": false
       },
       {
         "name": "htsAdverts",
         "enabled": false
       },
       {
         "name": "customerSatisfactionSurveys",
         "enabled": false
       },
       {
         "name": "findMyNinoAddToWallet",
         "enabled": false
       },
       {
         "name": "disableYourEmploymentIncomeChart",
         "enabled": true
       },
       {
         "name": "disableYourEmploymentIncomeChartAndroid",
         "enabled": true
       },
       {
         "name": "disableYourEmploymentIncomeChartIos",
         "enabled": true
       },
       {
         "name": "findMyNinoAddToGoogleWallet",
         "enabled": false
       } ],
  "urls" : {
    "manageGovGatewayIdUrl" : "www.url1.gov.uk",
    "tcNationalInsuranceRatesLettersUrl": "www.url6.gov.uk"
  },
  "clientId" : "AppClientId"
}""".stripMargin)
  override def config:      Map[String, Any] = super.config ++ Map[String, Any]("nameOfConfigFile" -> "INVALID_NAME")

  s"GET /status" should {
    "return valid response without a fullScreenInfoMessage" in {
      val response = await(wsUrl("/mobile-status/status?journeyId=7f1b5289-5f4d-4150-93a3-ff02dda28375").get)
      response.status                                        shouldBe 200
      (response.json \ "feature").as[List[FeatureFlag]].size shouldBe 11
      response.json                                          shouldBe expectedJsonResponse
    }
  }
}

class MobileStatusAppShutteredFullScreenMessageISpec extends BaseISpec {

  override def config: Map[String, Any] =
    super.config ++ Map[String, Any]("shuttering.appShuttered" -> true,
                                     "shuttering.title"        -> "QXBwIFVuYXZhaWxhYmxl",
                                     "shuttering.message"      -> "UGxlYXNlIHRyeSBhZ2FpbiBsYXRlci4=")

  s"GET /status" should {
    "return valid response without a fullScreenInfoMessage" in {
      val response = await(wsUrl("/mobile-status/status?journeyId=7f1b5289-5f4d-4150-93a3-ff02dda28375").get)
      response.status                                                            shouldBe 200
      (response.json \ "feature").as[List[FeatureFlag]].size                     shouldBe 11
      (response.json \ "fullScreenInfoMessage" \ "type").as[String]              shouldBe "Shutter"
      (response.json \ "fullScreenInfoMessage" \ "content" \ "title").as[String] shouldBe "App Unavailable"
      (response.json \ "fullScreenInfoMessage" \ "content" \ "body").as[String]  shouldBe "Please try again later."
    }
  }
}
