package snapple.thrift.serialization

import snapple.thrift.io.TVersionVector

import snapple.crdts.datatypes.VersionVector

import scala.collection.JavaConverters._

private[serialization] object VersionVectorSerializer {

  def serialize(versionVector: VersionVector): TVersionVector = {
    val strings = new java.util.ArrayList[String]
    val versions = new java.util.ArrayList[java.lang.Long]

    versionVector.versions.foreach {
      case (k, v) ⇒
        strings.add(k)
        versions.add(v)
    }

    new TVersionVector(strings, versions)
  }

  def deserialize(tversionVector: TVersionVector): VersionVector = {
    val versionKeys = tversionVector.versionKeys
    val versionValues = tversionVector.versionValues

    var map: Map[String, Long] = Map.empty

    for (i <- 0 until versionKeys.size) {
      val k = versionKeys.get(i)
      val v = versionValues.get(i)

      map = map + (k -> v)
    }

    VersionVector(map)
  }

}
