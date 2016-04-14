package snapple.cluster.io

import snapple.crdts.datatypes.{DataType, VersionVector, ORSet}

import snapple.cluster.KeyValueEntry

import snapple.thrift.io._

import snapple.thrift.serialization.DataSerializer

import java.util.{Map ⇒ JMap}

import scala.collection.JavaConverters._

object ThriftMethodHelper {

  def parsePropagate(values: JMap[String, TDataType]): Map[String, KeyValueEntry] = {
    var map: Map[String, KeyValueEntry] = Map.empty

    values.asScala.foreach {
      case (k, v) ⇒
        val (dataType, elementType) = DataSerializer.deserialize(v)
        map = map + (k → KeyValueEntry(dataType, elementType))
    }

    map
  }

  def parseCreateEntry(dataType: String, elementType: Int): KeyValueEntry = ThriftDataType(dataType) match {
    case ORSetDataType         ⇒ parseCreateORSetEntry(elementType)
    case VersionVectorDataType ⇒ KeyValueEntry(VersionVector.empty, NoElementType)
  }

  private def parseCreateORSetEntry(elementType: Int): KeyValueEntry = ThriftElementType(elementType) match {
    case NoElementType ⇒ throw new IllegalArgumentException(s"ORSets can't have $NoElementType")
    case other         ⇒ KeyValueEntry(ORSet.empty, other)
  }

  def convertGetEntry(optionalData: Option[KeyValueEntry]): TOptionalDataType = {
    val optionalDataType = new TOptionalDataType

    optionalData.foreach {
      case KeyValueEntry(dataType, elementType) ⇒
        val serialized = DataSerializer.serialize(dataType, elementType)
        optionalDataType.setDataType(serialized)
    }

    optionalDataType
  }

}
