package snapple.cluster.io

import snapple.crdts.datatypes.{DataType, VersionVector, ORSet}

import snapple.cluster.KeyValueEntry

import snapple.finagle.io._

import snapple.finagle.serialization.DataSerializer

object FinagleMethodHelper {

  def parsePropagate(values: Map[String, TDataType]): Map[String, KeyValueEntry] = values.map {
    case (k, v) =>
      val (dataType, elementKind) = DataSerializer.deserialize(v)
      (k -> KeyValueEntry(dataType, elementKind))
  }

  def parseCreateEntry(dataKind: String, elementKind: Int): KeyValueEntry = DataKind(dataKind) match {
    case ORSetDataKind         ⇒ parseCreateORSetEntry(elementKind)
    case VersionVectorDataKind ⇒ KeyValueEntry(VersionVector.empty, NoElementKind)
  }

  private def parseCreateORSetEntry(elementKind: Int): KeyValueEntry = ElementKind(elementKind) match {
    case NoElementKind ⇒ throw new IllegalArgumentException(s"ORSets can't have $NoElementKind")
    case other         ⇒ KeyValueEntry(ORSet.empty, other)
  }

  def convertGetEntry(optionalData: Option[KeyValueEntry]): TOptionalDataType = {
    val serialized = optionalData.map {
      case KeyValueEntry(dataType, elementType) ⇒ DataSerializer.serialize(dataType, elementType)
    }

    TOptionalDataType(serialized)
  }

}
