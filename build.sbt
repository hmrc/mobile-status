import play.sbt.PlayImport.PlayKeys.playDefaultPort
import sbt.Keys.testGrouping

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
    scalaVersion := "3.6.4",
    ThisBuild / crossScalaVersions ++= Seq("2.13.12", "3.6.4"),
    playDefaultPort := 8260,
    libraryDependencies ++= AppDependencies(),
    IntegrationTest / unmanagedSourceDirectories := (IntegrationTest / baseDirectory)(base =>
      Seq(base / "it", base / "test-common")
    ).value,
    IntegrationTest / parallelExecution := false,
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
    Seq("uk.gov.hmrc.mobilestatus.domain.types._", "uk.gov.hmrc.mobilestatus.domain.types.JourneyId._")
  )

// Coverage configuration
coverageMinimumStmtTotal := 80
coverageFailOnMinimum := true
coverageHighlighting := true
coverageExcludedPackages := "<empty>;com.kenshoo.play.metrics.*;.*definition.*;prod.*;testOnlyDoNotUseInAppConf.*;app.*;.*BuildInfo.*;.*Routes.*;.*javascript.*;.*Reverse.*;.*WSHttpImpl.*"
