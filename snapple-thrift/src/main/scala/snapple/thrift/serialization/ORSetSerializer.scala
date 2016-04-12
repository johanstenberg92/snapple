package snapple.thrift.serialization

import snapple.thrift.io._

import snapple.crdts.datatypes.{ORSet, VersionVector}

import scala.collection.JavaConverters._

import java.nio.ByteBuffer

private[serialization] object ORSetSerializer {

  def serialize(orset: ORSet[_], elementType: ThriftElementType): TORSet = {
    val byteBuffers = new java.util.ArrayList[ByteBuffer]
    val versionVectors = new java.util.ArrayList[TVersionVector]

    orset.elementsMap.foreach {
      case (k, v) =>
        byteBuffers.add(DataSerializer.serializeElementType(k))
        versionVectors.add(VersionVectorSerializer.serialize(v))
    }

    val tversionVector = VersionVectorSerializer.serialize(orset.versionVector)

    new TORSet(elementType.id, byteBuffers, versionVectors, tversionVector)
  }

  def deserialize(torset: TORSet): (ORSet[Any], ThriftElementType) = {
    val elementType = ThriftElementType(torset.getElementType)
    val versionVector = VersionVectorSerializer.deserialize(torset.getVersionVector)

    val elementKeys = torset.getElementKeys
    val elementValues = torset.getElementValues

    var map: Map[Any, VersionVector] = Map.empty

    for (i <- 0 until elementKeys.size) {
      val k = elementKeys.get(i)
      val v = elementValues.get(i)

      map = map + (DataSerializer.deserializeElementType(k, elementType) -> VersionVectorSerializer.deserialize(v))
    }

    (ORSet(map, versionVector), elementType)
  }

}
