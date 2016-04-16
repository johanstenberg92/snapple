package snapple.thrift.serialization

import snapple.thrift.io._

import snapple.crdts.datatypes.{DataType, VersionVector, ORSet}

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

object DataSerializer {

  private val ORSetId = 1
  private val VersionVectorId = 2

  def serialize(dataType: DataType, elementType: ThriftElementType = NoElementType): TDataType = dataType match {
    case orset: ORSet[_]              ⇒ TDataType.orset(ORSetSerializer.serialize(orset, elementType))
    case versionVector: VersionVector ⇒ TDataType.versionVector(VersionVectorSerializer.serialize(versionVector))
  }

  def deserialize(dataType: TDataType): (DataType, ThriftElementType) =
    dataType.getSetField.getThriftFieldId match {
      case ORSetId         ⇒ ORSetSerializer.deserialize(dataType.getOrset)
      case VersionVectorId ⇒ (VersionVectorSerializer.deserialize(dataType.getVersionVector), NoElementType)
    }

  @inline
  private[snapple] def serializeElementType(value: Any): ByteBuffer = value match {
    case boolean: Boolean ⇒
      val v: Byte = if (boolean) 1 else 0
      ByteBuffer.allocate(1).put(v)
    case byte: Byte     ⇒ ByteBuffer.allocate(1).put(byte)
    case int: Int       ⇒ ByteBuffer.allocate(4).putInt(int)
    case long: Long     ⇒ ByteBuffer.allocate(8).putLong(long)
    case double: Double ⇒ ByteBuffer.allocate(8).putDouble(double)
    case string: String ⇒ ByteBuffer.wrap(string.getBytes(StandardCharsets.UTF_8))
    case _              ⇒ throw new IllegalArgumentException(s"Invalid thrift data type $value")
  }

  @inline
  private[snapple] def deserializeElementType(bb: ByteBuffer, elementType: ThriftElementType): Any = {
    val positionedByteBuffer = bb.position(0).asInstanceOf[ByteBuffer]

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
      case NoElementType ⇒ throw new IllegalArgumentException("error deserializing no element type")
    }
  }

}
