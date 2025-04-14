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

package uk.gov.hmrc.mobilestatus.config

import com.google.inject.AbstractModule
import com.google.inject.name.Names.named
import play.api.{Configuration, Environment}

class GuiceModule(
  environment:   Environment,
  configuration: Configuration)
    extends AbstractModule {

  override def configure(): Unit = {
    bindConfigString("url.manageGovGatewayIdUrl")
    bindConfigBoolean("shuttering.appShuttered")
    bindConfigBoolean("feature.userPanelSignUp")
    bindConfigBoolean("feature.enablePushNotificationTokenRegistration")
    bindConfigBoolean("feature.paperlessAlertDialogs")
    bindConfigBoolean("feature.paperlessAdverts")
    bindConfigBoolean("feature.htsAdverts")
    bindConfigBoolean("feature.customerSatisfactionSurveys")
    bindConfigBoolean("feature.findMyNinoAddToWallet")
    bindConfigBoolean("feature.disableYourEmploymentIncomeChart")
    bindConfigBoolean("feature.disableYourEmploymentIncomeChartAndroid")
    bindConfigBoolean("feature.disableYourEmploymentIncomeChartIos")
    bindConfigBoolean("feature.findMyNinoAddToGoogleWallet")
    bindConfigBoolean("feature.useLegacyWebViewForIv")
    bindConfigInt("appAuthThrottle")
  }

  private def bindConfigBoolean(path: String): Unit =
    bindConstant().annotatedWith(named(path)).to(configuration.underlying.getBoolean(path))

  private def bindConfigString(path: String): Unit =
    bindConstant().annotatedWith(named(path)).to(configuration.underlying.getString(path))

  private def bindConfigInt(path: String): Unit =
    bindConstant().annotatedWith(named(path)).to(configuration.underlying.getInt(path))
}
