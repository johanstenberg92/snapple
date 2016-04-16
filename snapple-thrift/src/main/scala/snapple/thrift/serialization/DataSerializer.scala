package snapple.thrift.serialization

import snapple.thrift.io._

import snapple.crdts.datatypes.{DataType, VersionVector, ORSet}

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.StandardCharsets

object DataSerializer {

  private val ORSetId = 1
  private val VersionVectorId = 2

  private val ThriftByteOrder = ByteOrder.LITTLE_ENDIAN

  def serialize(dataType: DataType, elementType: ThriftElementType = NoElementType): TDataType = dataType match {
    case orset: ORSet[_]              ⇒ TDataType.orset(ORSetSerializer.serialize(orset, elementType))
    case versionVector: VersionVector ⇒ TDataType.versionVector(VersionVectorSerializer.serialize(versionVector))
  }

  def deserialize(dataType: TDataType): (DataType, ThriftElementType) =
    dataType.getSetField.getThriftFieldId match {
      case ORSetId         ⇒ ORSetSerializer.deserialize(dataType.getOrset)
      case VersionVectorId ⇒ (VersionVectorSerializer.deserialize(dataType.getVersionVector), NoElementType)
    }

  private[snapple] def serializeElementType(value: Any): ByteBuffer = value match {
    case boolean: Boolean ⇒
      val v: Byte = if (boolean) 1 else 0
      ByteBuffer.allocate(1).order(ThriftByteOrder).put(v)
    case byte: Byte     ⇒ ByteBuffer.allocate(1).order(ThriftByteOrder).put(byte)
    case int: Int       ⇒ ByteBuffer.allocate(4).order(ThriftByteOrder).putInt(int)
    case long: Long     ⇒ ByteBuffer.allocate(8).order(ThriftByteOrder).putLong(long)
    case double: Double ⇒ ByteBuffer.allocate(8).order(ThriftByteOrder).putDouble(double)
    case string: String ⇒ ByteBuffer.wrap(string.getBytes(StandardCharsets.UTF_8)).order(ThriftByteOrder)
    case _              ⇒ throw new IllegalArgumentException(s"Invalid thrift data type $value")
  }

  private[snapple] def deserializeElementType(bb: ByteBuffer, elementType: ThriftElementType): Any = {
    val positionedByteBuffer = bb.position(0).asInstanceOf[ByteBuffer].order(ThriftByteOrder)

    elementType match {
      case BooleanElementType ⇒ positionedByteBuffer.get == 1
      case ByteElementType    ⇒ positionedByteBuffer.get
      case IntElementType     ⇒ positionedByteBuffer.getInt
      case LongElementType    ⇒ positionedByteBuffer.getLong
      case DoubleElementType  ⇒ positionedByteBuffer.getDouble
      case StringElementType ⇒
        val bytes = Array.ofDim[Byte](positionedByteBuffer.remaining)
        positionedByteBuffer.get(bytes)
        new String(bytes, StandardCharsets.UTF_8)
      case other ⇒ throw new IllegalArgumentException(s"error deserializing $other")
    }
  }

}
