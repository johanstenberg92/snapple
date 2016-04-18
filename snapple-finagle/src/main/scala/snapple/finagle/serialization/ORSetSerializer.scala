package snapple.finagle.serialization

import snapple.finagle.io.{ElementKind, TORSet}

import snapple.crdts.datatypes.{ORSet, VersionVector}

private[serialization] object ORSetSerializer {

  def serialize(orset: ORSet[_], elementKind: ElementKind): TORSet = {
    val elementsMap = orset.elementsMap.map {
      case (k, v) => (DataSerializer.serializeElement(k), VersionVectorSerializer.serialize(v))
    }

    val tversionVector = VersionVectorSerializer.serialize(orset.versionVector)

    TORSet(elementKind.id, elementsMap, tversionVector)
  }

  def deserialize(torset: TORSet): (ORSet[Any], ElementKind) = {
    val elementKind = ElementKind(torset.elementType)

    val elementsMap = torset.elements.map {
      case (k, v) => (DataSerializer.deserializeElement(k, elementKind) -> VersionVectorSerializer.deserialize(v))
    }.toMap

    val versionVector = VersionVectorSerializer.deserialize(torset.versionVector)

    (ORSet(elementsMap, versionVector), elementKind)
  }

}
