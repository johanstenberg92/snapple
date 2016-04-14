package snapple.crdts.datatypes

import java.util.concurrent.atomic.AtomicLong

object VersionVector {

  val empty = VersionVector()

  def apply(host: String, timestamp: Long): VersionVector =
    new VersionVector(Map(host -> timestamp))

  private object Timestamp {
    val Zero = 0L
    val EndMarker = Long.MinValue
    val counter = new AtomicLong(1)
  }

}

case class VersionVector(private[snapple] val versions: Map[String, Long] = Map.empty) extends DataType {

  import VersionVector._

  override type S = VersionVector

  def +(host: String): VersionVector = {
    val value = Timestamp.counter.getAndIncrement
    VersionVector(versions.updated(host, value))
  }

  def versionAt(host: String): Long = versions.getOrElse(host, Timestamp.Zero)

  lazy val isEmpty: Boolean = versions.isEmpty

  def contains(host: String): Boolean = versions.contains(host)

  def size: Int = versions.size

  override def merge(that: VersionVector): VersionVector = {
    var merged = that.versions

    versions.foreach {
      case (host, timestamp) =>
        if (timestamp > merged.getOrElse(host, Timestamp.Zero)) merged = merged.updated(host, timestamp)
    }

    VersionVector(merged)
  }

  def <(other: VersionVector): Boolean = compareBefore(other)

  def >(other: VersionVector): Boolean = compareAfter(other)

  def <>(other: VersionVector): Boolean =
    !compareBefore(other) && !compareAfter(other) && this != other

  private def compareBefore(other: VersionVector): Boolean = compareTo(other, (a: Long, b: Long) => a < b)

  private def compareAfter(other: VersionVector): Boolean = compareTo(other, (a: Long, b: Long) => a > b)

  private def compareTo(other: VersionVector, cmp: (Long, Long) => Boolean): Boolean = {
    if (cmp(other.size, size)) false
    else {
      val allKeys = versions.keySet ++ other.versions.keySet
      var foundSpecial = false
      var count = 0

      allKeys.foreach {
        case key =>
          val version = versionAt(key)
          val otherVersion = other.versionAt(key)

          if (cmp(version, otherVersion) || version == otherVersion) count += 1
          if (cmp(version, otherVersion)) foundSpecial = true
      }

      count == allKeys.size && foundSpecial
    }
  }

}
