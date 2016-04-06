package snapple.crdts.datatypes

import java.util.concurrent.atomic.AtomicLong

object VersionVector {

  def apply(host: String, timestamp: Long): VersionVector =
    new VersionVector(Map(host -> timestamp))

  private object Timestamp {
    val Zero = 0L
    val EndMarker = Long.MinValue
    val counter = new AtomicLong(1)
  }

}

case class VersionVector(private val versions: Map[String, Long] = Map.empty) extends DataType {

  import VersionVector._

  override type S = VersionVector

  def +(host: String): VersionVector = {
    val value = Timestamp.counter.getAndIncrement
    VersionVector(versions.updated(host, value))
  }

  def versionAt(host: String): Long = versions.getOrElse(host, Timestamp.Zero)

  lazy val isEmpty: Boolean = versions.isEmpty

  def contains(host: String): Boolean = versions.contains(host)

  override def merge(that: VersionVector): VersionVector = {
    var merged = that.versions

    versions.foreach {
      case (host, timestamp) =>
        if (timestamp > merged.getOrElse(host, Timestamp.Zero)) merged = merged.updated(host, timestamp)
    }

    VersionVector(merged)
  }

  def <(other: VersionVector): Boolean = compareBefore(other)

  def >(other: VersionVector): Boolean = other.compareBefore(this)

  private def compareBefore(other: VersionVector): Boolean = {
    var foundSmaller = false
    var count = 0

    versions.foreach {
      case (key, version) =>
        val otherVersion = other.versionAt(key)

        if (version <= otherVersion) count += 1
        if (version < otherVersion) foundSmaller = true
    }

    count == versions.size || foundSmaller
  }

}
