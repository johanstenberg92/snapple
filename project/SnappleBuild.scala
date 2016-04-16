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

  lazy val ioTestsSettings = Seq(
    libraryDependencies ++= Seq(
      Dependencies.logbackLogging % "test"
    ),

    parallelExecution in Test := false
  )

  lazy val ioTests = Project(
    id = "snapple-io-tests",
    base = file("snapple-io-tests"),
    dependencies = Seq(client, cluster),
    settings = ioTestsSettings
  )

}
