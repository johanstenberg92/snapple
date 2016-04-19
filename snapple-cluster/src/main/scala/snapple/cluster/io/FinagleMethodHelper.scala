package snapple.cluster.io

import snapple.crdts.datatypes.{DataType, VersionVector, ORSet}

import snapple.cluster.KeyValueEntry

import snapple.finagle.io._

import snapple.finagle.serialization.DataSerializer

import scala.collection.{Map => SMap}

object FinagleMethodHelper {

  def parsePropagate(values: SMap[String, TDataType]): Map[String, KeyValueEntry] = values.map {
    case (k, v) =>
      val (dataType, elementKind) = DataSerializer.deserialize(v)
      (k -> KeyValueEntry(dataType, elementKind))
  }.toMap

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
      case KeyValueEntry(dataType, elementKind) ⇒ DataSerializer.serialize(dataType, elementKind)
    }

    TOptionalDataType(serialized)
  }

  def convertAllEntries(map: Map[String, KeyValueEntry]): Map[String, TDataType] = map.map {
    case (k, KeyValueEntry(d, e)) => (k -> DataSerializer.serialize(d, e))
  }

}
