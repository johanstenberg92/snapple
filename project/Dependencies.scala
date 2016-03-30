import sbt._

object Dependencies {

  object Versions {
    val Scala = "2.11.7"

    val ScalaTest = "2.2.6"

    val Thrift = "0.9.3"

    val GrizzledLogging = "1.0.3"
  }

  val scalaTest = "org.scalatest" %% "scalatest" % Versions.ScalaTest % "test"

  val thrift = "org.apache.thrift" % "libthrift" % Versions.Thrift

  val grizzledLogging = "org.clapper" %% "grizzled-slf4j" % Versions.GrizzledLogging

}
