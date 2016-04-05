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
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
  )

  lazy val root = Project(
    id = "snapple",
    base = file(".")
  )

  lazy val coreSettings = Seq(
    libraryDependencies ++= Seq(
      Dependencies.scalaTest,
      Dependencies.grizzledLogging,
      Dependencies.thrift
    )
  )

  lazy val core = Project(
    id = "snapple-core",
    base = file("snapple-core")
  ).settings(coreSettings: _*)

  lazy val cluster = Project(
    id = "snapple-cluster",
    base = file("snapple-cluster"),
    dependencies = Seq(core)
  )

  lazy val remote = Project(
    id = "snapple-remote",
    base = file("snapple-remote"),
    dependencies = Seq(core)
  )

}
