package snapple.thrift.serialization

import snapple.thrift.io.{TDataType, TORSet, TVersionVector}

import snapple.crdts.datatypes.{DataType, VersionVector, ORSet}

import scala.collection.JavaConverters._

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

sealed trait ThriftType { val id: Int }
case object NoElementType extends ThriftType { override val id: Int = 0 }
case object BooleanElementType extends ThriftType { override val id: Int = 1 }
case object ByteElementType extends ThriftType { override val id: Int = 2 }
case object IntElementType extends ThriftType { override val id: Int = 3 }
case object LongElementType extends ThriftType { override val id: Int = 4 }
case object DoubleElementType extends ThriftType { override val id: Int = 5 }
case object StringElementType extends ThriftType { override val id: Int = 6 }

object DataSerializer {

  private val ORSetId = 1
  private val VersionVectorId = 2

  def serialize(dataType: DataType, thriftType: ThriftType = NoElementType): TDataType = dataType match {
    case orset: ORSet[_] ⇒
      val torset = serializeORSet(orset, thriftType)
      ???
    case versionVector: VersionVector ⇒
      val tversionVector = serializeVersionVector(versionVector)
      ???
  }

  private def serializeORSet(orset: ORSet[_], thriftType: ThriftType): TORSet = {
    val elementsMap: Map[ByteBuffer, TVersionVector] = orset.elementsMap.map {
      case (k, v) ⇒ (serializeDataType(k), serializeVersionVector(v))
    }

    val tVersionVector = serializeVersionVector(orset.versionVector)

    new TORSet(thriftType.id, elementsMap.asJava, tVersionVector)
  }

  @inline
  private def serializeDataType(value: Any): ByteBuffer = value match {
    case boolean: Boolean =>
      val v: Byte = if (boolean) 1 else 0
      ByteBuffer.allocate(1).put(v)
    case byte: Byte =>
      ByteBuffer.allocate(1).put(byte)
    case int: Int =>
      ByteBuffer.allocate(4).putInt(int)
    case long: Long =>
      ByteBuffer.allocate(8).putLong(long)
    case double: Double =>
      ByteBuffer.allocate(8).putDouble(double)
    case string: String =>
      ByteBuffer.wrap(string.getBytes(StandardCharsets.UTF_8))
    case _ => throw new IllegalStateException(s"Invalid thrift data type $value") // TODO: check if warnings later if removed
  }

  private def serializeVersionVector(versionVector: VersionVector): TVersionVector = ???

  def deserialize(dataType: TDataType): DataType =
    dataType.getSetField.getThriftFieldId match {
      case ORSetId         ⇒ deserializeORSet(dataType.getOrset)
      case VersionVectorId ⇒ deserializeVersionVector(dataType.getVersionVector)
    }

  def deserializeORSet[T](orset: TORSet): ORSet[T] = ???

  def deserializeVersionVector(versionVector: TVersionVector): VersionVector = ???

}
