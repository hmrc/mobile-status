import sbt._

object AppDependencies {

  private val play30Bootstrap    = "8.5.0"
  private val playHmrcApiVersion = "8.0.0"

  private val refinedVersion   = "0.11.1"
  private val scalaMockVersion = "5.1.0"
  private val mockitoVersion   = "1.16.46"

  val compile = Seq(
    "uk.gov.hmrc" %% "bootstrap-backend-play-30" % play30Bootstrap,
    "uk.gov.hmrc" %% "play-hmrc-api-play-30"     % playHmrcApiVersion,
    "eu.timepit"  %% "refined"                   % refinedVersion
  )

  val test = Seq(
    "uk.gov.hmrc"            %% "bootstrap-test-play-28" % play30Bootstrap % "test, it",
    "org.mockito"            %% "mockito-scala"          % mockitoVersion  % "test",
  )

}
