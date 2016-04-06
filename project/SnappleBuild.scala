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

  lazy val crdtsSettings = Seq(
    libraryDependencies ++= Seq(
      Dependencies.scalaTest
    )
  )

  lazy val crdts = Project(
    id = "snapple-crdts",
    base = file("snapple-crdts")
  ).settings(crdtsSettings: _*)

  lazy val thriftSettings = Seq(
    libraryDependencies ++= Seq(
      Dependencies.scalaTest,
      Dependencies.grizzledLogging,
      Dependencies.thrift
    )
  )

  lazy val thrift = Project(
    id = "snapple-thrift",
    base = file("snapple-thrift"),
    dependencies = Seq(crdts)
  ).settings(thriftSettings: _*)

  lazy val cluster = Project(
    id = "snapple-cluster",
    base = file("snapple-cluster"),
    dependencies = Seq(thrift)
  )

  lazy val remote = Project(
    id = "snapple-remote",
    base = file("snapple-remote"),
    dependencies = Seq(thrift)
  )

}
