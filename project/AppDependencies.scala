import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  private val play26Bootstrap    = "5.1.0"
  private val playHmrcApiVersion = "6.2.0-play-27"

  private val pegdownVersion       = "1.6.0"
  private val wireMockVersion      = "2.27.2"
  private val scalaTestVersion     = "3.0.8"
  private val scalaTestPlusVersion = "4.0.3"
  private val mockitoVersion       = "3.6.28"
  private val refinedVersion       = "0.9.19"

  val compile = Seq(
    "uk.gov.hmrc" %% "bootstrap-backend-play-27" % play26Bootstrap,
    "uk.gov.hmrc" %% "play-hmrc-api"             % playHmrcApiVersion,
    "eu.timepit"  %% "refined"                   % refinedVersion
  )

  val test = Seq(
    "org.scalatest"          %% "scalatest"          % scalaTestVersion     % "test, it",
    "com.typesafe.play"      %% "play-test"          % current              % "test",
    "org.pegdown"            % "pegdown"             % pegdownVersion       % "test, it",
    "org.mockito"            % "mockito-core"        % mockitoVersion       % "test,it",
    "com.github.tomakehurst" % "wiremock"            % wireMockVersion      % "it",
    "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlusVersion % "test, it"
  )

}
