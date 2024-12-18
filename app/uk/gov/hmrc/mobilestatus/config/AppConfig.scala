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

import play.api.Configuration

import java.nio.charset.StandardCharsets
import javax.inject.{Inject, Singleton}

@Singleton
class AppConfig @Inject() (config: Configuration) {

  def nameOfConfigFile: Option[String] =
    config
      .getOptional[String]("nameOfConfigFile")

  def shutterTitle: String = configBase64String("shuttering.title").getOrElse("")

  def shutterBody: Option[String] = configBase64String("shuttering.message")

  def shutterTitleCy: Option[String] = configBase64String("shuttering.titleCy")

  def shutterBodyCy: Option[String] = configBase64String("shuttering.messageCy")

  def configBase64String(path: String): Option[String] = {
    val encoded = config.underlying.getString(path)
    if (encoded.isEmpty) None else Some(Base64.decode(encoded))
  }

}

object Base64 {
  private val decoder = java.util.Base64.getDecoder

  def decode(encoded: String): String =
    new String(decoder.decode(encoded), StandardCharsets.UTF_8)
}