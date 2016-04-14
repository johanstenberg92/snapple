package snapple.thrift.io

import snapple.crdts.datatypes.{DataType, ORSet, VersionVector}

import snapple.thrift.serialization.DataSerializer

import java.nio.ByteBuffer

case class ThriftOpHandler(host: String) {

  def handleOp(
    dataType:    ThriftDataType,
    elementType: ThriftElementType,
    operation:   ThriftOpType,
    buffer:      ByteBuffer
  ): (DataType ⇒ DataType) = dataType match {
    case ORSetDataType         ⇒ handleORSetOp(elementType, operation, buffer)
    case VersionVectorDataType ⇒ versionVectorOp
  }

  private[io] def handleORSetOp(
    elementType: ThriftElementType,
    operation:   ThriftOpType,
    buffer:      ByteBuffer
  ): (DataType ⇒ DataType) = operation match {
    case ThriftAddOpType ⇒
      val element = DataSerializer.deserializeElementType(buffer, elementType)
      (dataType: DataType) ⇒ (dataType.asInstanceOf[ORSet[Any]] + (host, element))
    case ThriftRemoveOpType ⇒
      val element = DataSerializer.deserializeElementType(buffer, elementType)
      (dataType: DataType) ⇒ (dataType.asInstanceOf[ORSet[Any]] - (host, element))
    case ThriftClearOpType ⇒
      (dataType: DataType) ⇒ dataType.asInstanceOf[ORSet[Any]].clear
  }

  private[io] lazy val versionVectorOp: (DataType ⇒ DataType) = (dataType: DataType) ⇒ {
    dataType.asInstanceOf[VersionVector] + host
  }

}
