import sbt._

object AppDependencies {

  private val play30Bootstrap    = "9.13.0"
  private val playHmrcApiVersion = "8.2.0"

  private val refinedVersion   = "0.11.3"
  private val scalaMockVersion = "6.2.0"
  private val mockitoVersion   = "1.17.31"

  val compile = Seq(
    "uk.gov.hmrc" %% "bootstrap-backend-play-30" % play30Bootstrap,
    "uk.gov.hmrc" %% "play-hmrc-api-play-30"     % playHmrcApiVersion,
    "eu.timepit"  %% "refined"                   % refinedVersion
  )

  trait TestDependencies {
    lazy val scope: String        = "test"
    lazy val test:  Seq[ModuleID] = ???
  }

  private def testCommon(scope: String) = Seq(
    "uk.gov.hmrc" %% "bootstrap-test-play-30" % play30Bootstrap % scope
  )

  object Test {

    def apply(): Seq[ModuleID] =
      new TestDependencies {

        override lazy val test = testCommon(scope) ++ Seq(
            "org.scalamock" %% "scalamock" % scalaMockVersion % scope
          )
      }.test
  }

  object IntegrationTest {

    def apply(): Seq[ModuleID] =
      new TestDependencies {

        override lazy val scope = "it"

        override lazy val test = testCommon(scope) ++ Seq.empty
      }.test

  }

  def apply(): Seq[ModuleID] = compile ++ Test() ++ IntegrationTest()

}
