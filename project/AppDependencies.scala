import sbt._

object AppDependencies {

  private val play28Bootstrap    = "7.20.0"
  private val playHmrcApiVersion = "7.2.0-play-28"

  private val pegdownVersion   = "1.6.0"
  private val wireMockVersion  = "2.27.2"
  private val mockitoVersion   = "1.16.46"
  private val refinedVersion   = "0.9.26"
  private val scalaMockVersion = "5.1.0"

  val compile = Seq(
    "uk.gov.hmrc" %% "bootstrap-backend-play-28" % play28Bootstrap,
    "uk.gov.hmrc" %% "play-hmrc-api"             % playHmrcApiVersion,
    "eu.timepit"  %% "refined"                   % refinedVersion
  )

  val test = Seq(
    "org.pegdown"            % "pegdown"                 % pegdownVersion  % "test, it",
    "com.github.tomakehurst" % "wiremock"                % wireMockVersion % "it",
    "org.mockito"            %% "mockito-scala"          % mockitoVersion  % "test",
    "uk.gov.hmrc"            %% "bootstrap-test-play-28" % play28Bootstrap % "test, it"
  )

}
