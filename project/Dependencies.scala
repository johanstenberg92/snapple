import sbt._

object Dependencies {

  object Versions {
    val Scala = "2.11.8"

    val ScalaTest = "2.2.6"

    val GrizzledLogging = "1.0.3"

    val Scopt = "3.4.0"

    val Logback = "1.1.7"

    val Finagle = "6.34.0"
  }

  val scalaTest = "org.scalatest" %% "scalatest" % Versions.ScalaTest % "test"

  val grizzledLogging = "org.clapper" %% "grizzled-slf4j" % Versions.GrizzledLogging

  val scopt = "com.github.scopt" %% "scopt" % Versions.Scopt

  val logbackLogging = "ch.qos.logback" % "logback-classic" % Versions.Logback

  val finagleThrift = "com.twitter" %% "finagle-thrift" % Versions.Finagle
}
