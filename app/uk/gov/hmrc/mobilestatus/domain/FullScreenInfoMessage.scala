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

package uk.gov.hmrc.mobilestatus.domain

import play.api.libs.json.{Format, JsError, JsString, JsSuccess, Json, OFormat, Reads, Writes}

import java.util.UUID

case class FullScreenInfoMessage(
  id:      String,
  `type`:  String,
  content: Content,
  links:   Option[Seq[Link]] = None)

object FullScreenInfoMessage {
  implicit val formats: Format[FullScreenInfoMessage] = Json.format[FullScreenInfoMessage]

  def shutterApp(
    title: String,
    body:  Option[String]
  ): FullScreenInfoMessage = FullScreenInfoMessage(UUID.randomUUID().toString, "Shutter", Content(title, body))
}

case class Content(
  title: String,
  body:  Option[String] = None)

object Content {
  implicit val inAppMessages: OFormat[Content] = Json.format[Content]
}

case class Link(
  url:                        Option[String],
  urlType:                    UrlType,
  `type`:                     ButtonType,
  message:                    String,
  androidCampaignQueryString: Option[String] = None,
  iosCampaignQueryString:     Option[String] = None)

object Link {
  implicit val inAppMessages: OFormat[Link] = Json.format[Link]
}

sealed trait UrlType

case object Sso extends UrlType

case object Normal extends UrlType

case object InApp extends UrlType

case object NewScreen extends UrlType

case object Dismiss extends UrlType

object UrlType {

  implicit val reads: Reads[UrlType] = Reads {
    case JsString("Sso")       => JsSuccess(Sso)
    case JsString("Normal")    => JsSuccess(Normal)
    case JsString("InApp")     => JsSuccess(InApp)
    case JsString("NewScreen") => JsSuccess(NewScreen)
    case JsString("Dismiss")   => JsSuccess(Dismiss)
    case _                     => JsError(s"Invalid UrlType")
  }

  implicit val writes: Writes[UrlType] = Writes {
    case Sso       => JsString("Sso")
    case Normal    => JsString("Normal")
    case InApp     => JsString("InApp")
    case NewScreen => JsString("NewScreen")
    case Dismiss   => JsString("Dismiss")
    case e         => throw new IllegalStateException(s"$e is an Invalid UrlType")
  }
}

sealed trait ButtonType

case object Primary extends ButtonType

case object Secondary extends ButtonType

object ButtonType {

  implicit val reads: Reads[ButtonType] = Reads {
    case JsString("Primary")   => JsSuccess(Primary)
    case JsString("Secondary") => JsSuccess(Secondary)
    case _                     => JsError(s"Invalid type")
  }

  implicit val writes: Writes[ButtonType] = Writes {
    case Primary   => JsString("Primary")
    case Secondary => JsString("Secondary")
    case e         => throw new IllegalStateException(s"$e is an Invalid type")

  }
}
