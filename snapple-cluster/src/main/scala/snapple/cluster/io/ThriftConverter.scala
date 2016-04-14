package snapple.cluster.io

import snapple.crdts.datatypes.{DataType, VersionVector, ORSet}

import snapple.thrift.io._

import snapple.thrift.serialization.DataSerializer

import java.util.{Map ⇒ JMap}

import scala.collection.JavaConverters._

object ThriftConverter {

  def parsePropagate(values: JMap[String, TDataType]): Map[String, (DataType, ThriftElementType)] = {
    var map: Map[String, (DataType, ThriftElementType)] = Map.empty

    values.asScala.foreach {
      case (k, v) ⇒
        map = map + (k → DataSerializer.deserialize(v))
    }

    map
  }

  def parseCreateEntry(dataType: String, elementType: Int): (DataType, ThriftElementType) = ThriftDataType(dataType) match {
    case ORSetDataType         ⇒ parseCreateORSetEntry(elementType)
    case VersionVectorDataType ⇒ (VersionVector.empty, NoElementType)
  }

  private def parseCreateORSetEntry(elementType: Int): (DataType, ThriftElementType) = ThriftElementType(elementType) match {
    case NoElementType ⇒ throw new IllegalArgumentException(s"ORSets can't have $NoElementType")
    case other         ⇒ (ORSet.empty, other)
  }

  def convertGetEntry(optionalData: Option[(DataType, ThriftElementType)]): TOptionalDataType = {
    val optionalDataType = new TOptionalDataType

    optionalData.foreach {
      case (dataType, elementType) ⇒
        val serialized = DataSerializer.serialize(dataType, elementType)
        optionalDataType.setDataType(serialized)
    }

    optionalDataType
  }
}
