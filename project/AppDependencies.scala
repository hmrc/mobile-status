import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  private val play28Bootstrap    = "7.13.0"
  private val playHmrcApiVersion = "7.1.0-play-28"

  private val pegdownVersion       = "1.6.0"
  private val wireMockVersion      = "2.27.2"
  private val scalaTestVersion     = "3.2.9"
  private val scalaTestPlusVersion = "5.1.0"
  private val mockitoVersion       = "1.16.46"
  private val refinedVersion       = "0.9.26"
  private val scalaMockVersion     = "5.1.0"
  private val flexmarkAllVersion   = "0.36.8"

  val compile = Seq(
    "uk.gov.hmrc" %% "bootstrap-backend-play-28" % play28Bootstrap,
    "uk.gov.hmrc" %% "play-hmrc-api"             % playHmrcApiVersion,
    "eu.timepit"  %% "refined"                   % refinedVersion
  )

  val test = Seq(
    "org.scalatest"          %% "scalatest"          % scalaTestVersion     % "test, it",
    "com.typesafe.play"      %% "play-test"          % current              % "test",
    "org.pegdown"            % "pegdown"             % pegdownVersion       % "test, it",
    "com.github.tomakehurst" % "wiremock"            % wireMockVersion      % "it",
    "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlusVersion % "test, it",
    "org.mockito"            %% "mockito-scala"      % mockitoVersion       % "test",
    "com.vladsch.flexmark"   % "flexmark-all"        % flexmarkAllVersion   % "test"
  )

}
