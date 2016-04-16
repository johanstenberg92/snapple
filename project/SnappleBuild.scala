import sbt._

import sbt.Keys._

import com.typesafe.sbt.SbtMultiJvm
import com.typesafe.sbt.SbtMultiJvm.MultiJvmKeys.MultiJvm

object SnappleBuild extends Build {

  val Organization = "snapple"

  val Version = "0.0.1"

  override lazy val settings = super.settings ++ Seq(
    organization := Organization,
    version := Version,
    scalaVersion := Dependencies.Versions.Scala,
    shellPrompt := { s ⇒ Project.extract(s).currentProject.id + " > " },
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    libraryDependencies ++= Seq(
      Dependencies.scalaTest
    )
  )

  lazy val root = Project(
    id = "snapple",
    base = file(".")
  )

  lazy val crdts = Project(
    id = "snapple-crdts",
    base = file("snapple-crdts")
  )

  lazy val thriftSettings = Seq(
    libraryDependencies ++= Seq(
      Dependencies.grizzledLogging,
      Dependencies.thrift
    )
  )

  lazy val thrift = Project(
    id = "snapple-thrift",
    base = file("snapple-thrift"),
    dependencies = Seq(crdts),
    settings = thriftSettings
  )

  lazy val clusterSettings = Seq(
    libraryDependencies ++= Seq(
      Dependencies.logbackLogging,
      Dependencies.scopt
    )
  )

  lazy val cluster = Project(
    id = "snapple-cluster",
    base = file("snapple-cluster"),
    dependencies = Seq(thrift),
    settings = clusterSettings
  )

  lazy val client = Project(
    id = "snapple-client",
    base = file("snapple-client"),
    dependencies = Seq(thrift)
  )

  lazy val ioTestsSettings = SbtMultiJvm.multiJvmSettings ++ Seq(
    libraryDependencies ++= Seq(
      Dependencies.logbackLogging % "test"
    ),

    compile in MultiJvm <<= (compile in MultiJvm) triggeredBy (compile in Test),
    parallelExecution in Test := false,

    executeTests in Test <<= (executeTests in Test, executeTests in MultiJvm) map {
        case (testResults, multiNodeResults) =>
          val overall =
            if (testResults.overall.id < multiNodeResults.overall.id) multiNodeResults.overall
            else testResults.overall

          Tests.Output(
            overall,
            testResults.events ++ multiNodeResults.events,
            testResults.summaries ++ multiNodeResults.summaries
          )
    }
  )

  // Nice guide about multi-jvm tests: http://doc.akka.io/docs/akka/current/dev/multi-jvm-testing.html
  lazy val ioTests = Project(
    id = "snapple-io-tests",
    base = file("snapple-io-tests"),
    dependencies = Seq(client, cluster),
    settings = ioTestsSettings
  ) configs (MultiJvm)

}
