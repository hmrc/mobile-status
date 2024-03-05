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

import javax.inject.{Inject, Named}
import uk.gov.hmrc.mobilestatus.domain.{FeatureFlag, FullScreenInfoMessage, StatusResponse, Urls}
import uk.gov.hmrc.mobilestatus.config.FullScreenMessageConfigJson

class StatusService @Inject() (
  fullScreenMessageConfigJson:                                                                       FullScreenMessageConfigJson,
  @Named("url.manageGovGatewayIdUrl") manageGovGatewayIdUrl:                                         String,
  @Named("feature.userPanelSignUp") userPanelSignUp:                                                 Boolean,
  @Named("feature.enablePushNotificationTokenRegistration") enablePushNotificationTokenRegistration:   Boolean,
  @Named("feature.paperlessAlertDialogs") paperlessAlertDialogs:                                     Boolean,
  @Named("feature.paperlessAdverts") paperlessAdverts:                                               Boolean,
  @Named("feature.htsAdverts") htsAdverts:                                                           Boolean,
  @Named("feature.customerSatisfactionSurveys") customerSatisfactionSurveys:                         Boolean,
  @Named("feature.findMyNinoAddToWallet") findMyNinoAddToWallet:                                       Boolean,
  @Named("feature.disableYourEmploymentIncomeChart") disableYourEmploymentIncomeChart:               Boolean,
  @Named("feature.disableYourEmploymentIncomeChartAndroid") disableYourEmploymentIncomeChartAndroid: Boolean,
  @Named("feature.disableYourEmploymentIncomeChartIos") disableYourEmploymentIncomeChartIos:         Boolean,
  @Named("feature.findMyNinoAddToGoogleWallet") findMyNinoAddToGoogleWallet:                           Boolean,
  @Named("clientId") clientId:                                                                       String,
  @Named("appAuthThrottle") appAuthThrottle:                                                         Int) {

  private val featureFlags: List[FeatureFlag] = List(
    FeatureFlag("userPanelSignUp", userPanelSignUp),
    FeatureFlag("enablePushNotificationTokenRegistration", enablePushNotificationTokenRegistration),
    FeatureFlag("paperlessAlertDialogs", paperlessAlertDialogs),
    FeatureFlag("paperlessAdverts", paperlessAdverts),
    FeatureFlag("htsAdverts", htsAdverts),
    FeatureFlag("customerSatisfactionSurveys", customerSatisfactionSurveys),
    FeatureFlag("findMyNinoAddToWallet", findMyNinoAddToWallet),
    FeatureFlag("disableYourEmploymentIncomeChart", disableYourEmploymentIncomeChart),
    FeatureFlag("disableYourEmploymentIncomeChartAndroid", disableYourEmploymentIncomeChartAndroid),
    FeatureFlag("disableYourEmploymentIncomeChartIos", disableYourEmploymentIncomeChartIos),
    FeatureFlag("findMyNinoAddToGoogleWallet", findMyNinoAddToGoogleWallet)
  )

  private val urls: Urls =
    Urls(manageGovGatewayIdUrl)

  def buildStatusResponse(): StatusResponse = {
    val fullScreenMessage: Option[FullScreenInfoMessage] = fullScreenMessageConfigJson.readMessageConfigJson
    StatusResponse(featureFlags, urls, clientId, appAuthThrottle, fullScreenMessage)
  }

}
