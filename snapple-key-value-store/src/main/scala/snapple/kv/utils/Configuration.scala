package snapple.kv.utils

import grizzled.slf4j.Logger

import java.util.UUID

object Configuration {

  private val DefaultPort = 9000

  private val logger = Logger[this.type]

  lazy val Port: Int = sys.env.get("SNAPPLE_PORT").flatMap(toInt).getOrElse(DefaultPort)

  private def toInt(s: String): Option[Int] = try {
    Some(s.toInt)
  } catch {
    case e: Exception => None
  }

  lazy val StdOutputLogging = sys.env.get("SNAPPLE_STD_OUTPUT_LOGGING") == Some("true")

  lazy val ReplicaAddresses: List[(String, Int)] = try {
    sys.env.get("SNAPPLE_CLUSTER_HOSTS").map {
      case s =>
        s.split(",").toList.map {
          case h =>
            val r = h.split(":")
            (r(0), r(1).toInt)
        }
    }.getOrElse(Nil)
  } catch {
    case e: Exception =>
      val s = sys.env("SNAPPLE_CLUSTER_HOSTS")
      logger.error(s"error parsing String $s.", e)
      Nil
  }

  lazy val UniqueId = sys.env.get("SNAPPLE_UNIQUE_ID").getOrElse(UUID.randomUUID)
}
