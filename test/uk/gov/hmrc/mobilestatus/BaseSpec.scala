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

package uk.gov.hmrc.mobilestatus

import org.apache.pekko.actor.ClassicActorSystemProvider
import org.apache.pekko.stream.{Materializer, SystemMaterializer}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatest.OptionValues
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.{DefaultAwaitTimeout, FutureAwaits}
import uk.gov.hmrc.mobilestatus.config.AppConfig
import uk.gov.hmrc.mobilestatus.domain.{Content, FullScreenInfoMessage}

import scala.concurrent.ExecutionContext

trait BaseSpec
    extends AnyWordSpecLike
    with Matchers
    with FutureAwaits
    with DefaultAwaitTimeout
    with OptionValues
    with MockitoSugar
    with GuiceOneAppPerSuite {

  val appInjector = app.injector

  implicit def matFromSystem(implicit provider: ClassicActorSystemProvider): Materializer =
    SystemMaterializer(provider.classicSystem).materializer
  implicit val executionContext: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  implicit def appConfig: AppConfig = appInjector.instanceOf[AppConfig]

  val fullScreenMessage = FullScreenInfoMessage(id = "id", `type` = "type", content = Content("title"))

  val fullScreenMessageWelsh = FullScreenInfoMessage(id = "id",
                                                     `type`    = "type",
                                                     content   = Content("title"),
                                                     contentCy = Some(Content("title welsh")))
  val appAuthThrottle = 0

}
