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
  @Named("url.manageGovGatewayIdUrl") manageGovGatewayIdUrl:       String,
  fullScreenMessageConfigJson:                                     FullScreenMessageConfigJson) {

  private val featureFlags: List[FeatureFlag] = List.empty

  private val urls: Urls =
    Urls(manageGovGatewayIdUrl)

  def buildStatusResponse(): StatusResponse = {
    val fullScreenMessage: Option[FullScreenInfoMessage] = fullScreenMessageConfigJson.readMessageConfigJson
    StatusResponse(featureFlags, urls, fullScreenMessage)
  }

}
