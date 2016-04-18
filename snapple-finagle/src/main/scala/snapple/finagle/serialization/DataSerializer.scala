package snapple.finagle.serialization

import snapple.finagle.io._

import snapple.crdts.datatypes.{DataType, VersionVector, ORSet}

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.StandardCharsets

object DataSerializer {

  private val ORSetId = 1
  private val VersionVectorId = 2

  private[finagle] val FinagleByteOrder = ByteOrder.BIG_ENDIAN

  def serialize(dataType: DataType, elementKind: ElementKind = NoElementKind): TDataType = dataType match {
    case orset: ORSet[_]              ⇒ TDataType.Orset(ORSetSerializer.serialize(orset, elementKind))
    case versionVector: VersionVector ⇒ TDataType.VersionVector(VersionVectorSerializer.serialize(versionVector))
  }

  def deserialize(dataType: TDataType): (DataType, ElementKind) =
    dataType match {
      case TDataType.Orset(orset)                 ⇒ ORSetSerializer.deserialize(orset)
      case TDataType.VersionVector(versionVector) ⇒ (VersionVectorSerializer.deserialize(versionVector), NoElementKind)
      case _                                      ⇒ throw new IllegalArgumentException(s"can't deserialize $dataType")
    }

  private[snapple] def serializeElement(value: Any): ByteBuffer = {
    val bb = value match {
      case boolean: Boolean ⇒
        val v: Byte = if (boolean) 1 else 0
        ByteBuffer.allocate(1).order(FinagleByteOrder).put(v)
      case byte: Byte     ⇒ ByteBuffer.allocate(1).order(FinagleByteOrder).put(byte)
      case int: Int       ⇒ ByteBuffer.allocate(4).order(FinagleByteOrder).putInt(int)
      case long: Long     ⇒ ByteBuffer.allocate(8).order(FinagleByteOrder).putLong(long)
      case double: Double ⇒ ByteBuffer.allocate(8).order(FinagleByteOrder).putDouble(double)
      case string: String ⇒ ByteBuffer.wrap(string.getBytes(StandardCharsets.UTF_8))
      case _              ⇒ throw new IllegalArgumentException(s"Invalid thrift data type $value")
    }

    bb.rewind.asInstanceOf[ByteBuffer]
  }

  private[snapple] def deserializeElement(bb: ByteBuffer, elementKind: ElementKind): Any = {
    val positionedByteBuffer = bb.order(FinagleByteOrder)

    elementKind match {
      case BooleanElementKind ⇒ positionedByteBuffer.get == 1
      case ByteElementKind    ⇒ positionedByteBuffer.get
      case IntElementKind     ⇒ positionedByteBuffer.getInt
      case LongElementKind    ⇒ positionedByteBuffer.getLong
      case DoubleElementKind  ⇒ positionedByteBuffer.getDouble
      case StringElementKind ⇒
        val bytes = Array.ofDim[Byte](positionedByteBuffer.remaining)
        positionedByteBuffer.get(bytes)
        new String(bytes, StandardCharsets.UTF_8)
      case other ⇒ throw new IllegalArgumentException(s"error deserializing $other")
    }
  }

}
