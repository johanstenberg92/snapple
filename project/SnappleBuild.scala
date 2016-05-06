import sbt._

import sbt.Keys._

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

  lazy val finagleSettings = Seq(
    libraryDependencies ++= Seq(
      Dependencies.grizzledLogging,
      Dependencies.finagleThrift
    )
  )

  lazy val finagle = Project(
    id = "snapple-finagle",
    base = file("snapple-finagle"),
    dependencies = Seq(crdts),
    settings = finagleSettings
  )

  lazy val clusterSettings = Seq(
    libraryDependencies ++= Seq(
      Dependencies.logbackLogging,
      Dependencies.scopt
    ),

    fork in run := true
  )

  lazy val cluster = Project(
    id = "snapple-cluster",
    base = file("snapple-cluster"),
    dependencies = Seq(finagle),
    settings = clusterSettings
  )

  lazy val client = Project(
    id = "snapple-client",
    base = file("snapple-client"),
    dependencies = Seq(finagle)
  )

  lazy val ioTestsSettings = Seq(
    libraryDependencies ++= Seq(
      Dependencies.logbackLogging % "test",
      Dependencies.finagleThrift % "test"
    ),

    parallelExecution in Test := false,
    fork := true
  )

  lazy val ioTests = Project(
    id = "snapple-io-tests",
    base = file("snapple-io-tests"),
    dependencies = Seq(client, cluster),
    settings = ioTestsSettings
  )

  lazy val cliSettings = Seq(
    libraryDependencies ++= Seq(
      Dependencies.logbackLogging,
      Dependencies.scopt
    ),

    fork in run := true
  )

  lazy val cli = Project(
    id = "snapple-cli",
    base = file("snapple-cli"),
    dependencies = Seq(client),
    settings = cliSettings
  )

  lazy val benchmarkSettings = Seq(
    libraryDependencies ++= Seq(
      Dependencies.logbackLogging,
      Dependencies.scopt,
      Dependencies.apacheCommons
    ),

    fork in run := true
  )

  lazy val benchmark = Project(
    id = "snapple-benchmark",
    base = file("snapple-benchmark"),
    dependencies = Seq(client),
    settings = benchmarkSettings
  )

}
