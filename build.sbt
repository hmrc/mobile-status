import play.sbt.PlayImport.PlayKeys.playDefaultPort
import sbt.Keys.testGrouping
import sbt.Tests.{Group, SubProcess}

val appName = "mobile-status"

lazy val microservice = Project(appName, file("."))
  .enablePlugins(
    play.sbt.PlayScala,
    SbtAutoBuildPlugin,
    SbtDistributablesPlugin
  )
  .configs(IntegrationTest)
  .settings(inConfig(IntegrationTest)(Defaults.itSettings): _*)
  .settings(
    majorVersion := 0,
    scalaVersion := "2.13.8",
    playDefaultPort := 8260,
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    IntegrationTest / unmanagedSourceDirectories := (IntegrationTest / baseDirectory)(base =>
      Seq(base / "it", base / "test-common")
    ).value,
    IntegrationTest / testGrouping := oneForkedJvmPerTest(
      (IntegrationTest / definedTests).value
    ),
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding",
      "UTF-8",
      "-language:higherKinds",
      "-language:postfixOps",
      "-feature",
      "-Xlint",
      "-Wconf:src=routes/.*:s"
    )
  )
  .settings(
    routesImport ++=
      Seq("uk.gov.hmrc.mobilestatus.domain.types._", "uk.gov.hmrc.mobilestatus.domain.types.ModelTypes._")
  )
  .settings(resolvers += Resolver.jcenterRepo)

// Coverage configuration
coverageMinimumStmtTotal := 80
coverageFailOnMinimum := true
coverageHighlighting := true
coverageExcludedPackages := "<empty>;com.kenshoo.play.metrics.*;.*definition.*;prod.*;testOnlyDoNotUseInAppConf.*;app.*;.*BuildInfo.*;.*Routes.*;.*javascript.*;.*Reverse.*;.*WSHttpImpl.*"

def oneForkedJvmPerTest(tests: Seq[TestDefinition]) =
  tests map { test =>
    Group(test.name, Seq(test), SubProcess(ForkOptions().withRunJVMOptions(Vector(s"-Dtest.name=${test.name}"))))
  }
