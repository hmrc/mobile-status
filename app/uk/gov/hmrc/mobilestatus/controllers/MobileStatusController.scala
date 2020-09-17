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

import com.typesafe.config.ConfigException
import javax.inject.{Inject, Singleton}
import play.api.Logger
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, AnyContent, BodyParser, ControllerComponents, Result}
import uk.gov.hmrc.api.controllers.{ErrorInternalServerError, HeaderValidator}
import uk.gov.hmrc.mobilestatus.domain.types.ModelTypes.JourneyId
import uk.gov.hmrc.mobilestatus.service.StatusService
import uk.gov.hmrc.play.bootstrap.controller.{BackendBaseController, BackendController}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

trait MobileStatusController extends BackendBaseController with HeaderValidator {
  implicit def executionContext: ExecutionContext

  def status(journeyId: JourneyId): Action[JsValue] =
    validateAccept(acceptHeaderValidationRules).async(controllerComponents.parsers.json) { implicit request =>
      getAppStatus(journeyId)
    }

  def getAppStatus(journeyId: JourneyId): Future[Result]
}

@Singleton
class LiveMobileStatusController @Inject() (
  cc:                            ControllerComponents,
  statusService:                 StatusService
)(implicit val executionContext: ExecutionContext)
    extends BackendController(cc)
    with MobileStatusController {

  override def parser: BodyParser[AnyContent] = cc.parsers.anyContent

  def getAppStatus(journeyId: JourneyId): Future[Result] =
      Try(statusService.buildStatusResponse()) match {
        case Success(result) => Future.successful(Ok(Json.toJson(result)))

        case Failure(e) =>
          e match {
            case _: ConfigException =>
              Logger.error("Config not found!")
              Future.successful(
                InternalServerError(Json.toJson(ErrorInternalServerError))
              )

            case e: Exception =>
              Logger.warn(
                s"Native Error - Shuttering Controller Internal server error: ${e.getMessage}",
                e
              )
              Future.successful(
                InternalServerError(Json.toJson(ErrorInternalServerError))
              )
          }
      }
}
