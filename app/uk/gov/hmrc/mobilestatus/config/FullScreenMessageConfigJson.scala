/*
 * Copyright 2022 HM Revenue & Customs
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

import com.google.inject.name.Named
import play.api.Logger
import play.api.libs.json.Json
import uk.gov.hmrc.mobilestatus.domain.FullScreenInfoMessage

import java.io.InputStream
import scala.io.Source
import javax.inject.{Inject, Singleton}

@Singleton
class FullScreenMessageConfigJson @Inject() (
  appConfig:                                      AppConfig,
  @Named("shuttering.appShuttered") appShuttered: Boolean) {

  val logger: Logger = Logger(this.getClass)

  def readMessageConfigJson: Option[FullScreenInfoMessage] =
    if (appShuttered) Some(FullScreenInfoMessage.shutterApp(appConfig.shutterTitle, appConfig.shutterBody))
    else
      findResource(s"/resources/mobilestatus/${appConfig.nameOfConfigFile.getOrElse("")}.json").map(
        Json
          .parse(_)
          .as[FullScreenInfoMessage]
      )

  def findResource(path: String): Option[String] = {
    val resource = getClass.getResourceAsStream(path)
    if (resource == null) {
      logger.info(s"Could not find resource '$path'")
      None
    } else {
      Some(readStreamToString(resource))
    }
  }

  private def readStreamToString(is: InputStream) =
    try Source.fromInputStream(is).mkString
    finally is.close()
}
