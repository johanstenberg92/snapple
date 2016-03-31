import sbt._

import sbt.Keys._

object SnappleBuild extends Build {

  val Organization = "snapple"

  val Version = "0.0.1"

  override lazy val settings = super.settings ++ Seq(
    organization := Organization,
    version := Version,
    scalaVersion := Dependencies.Versions.Scala,
    shellPrompt := { s => Project.extract(s).currentProject.id + " > " },
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
  )

  lazy val root = Project(
    id = "snapple",
    base = file(".")
  )

  lazy val keyValueStoreDependencies = Seq(
    libraryDependencies ++= Seq(
      Dependencies.scalaTest,
      Dependencies.grizzledLogging,
      Dependencies.thrift
    )
  )

  lazy val keyValueStore = Project(id = "snapple-key-value-store", base = file("snapple-key-value-store"))
    .settings(keyValueStoreDependencies: _*)

}
