package snapple.finagle.io

import snapple.crdts.datatypes.{DataType, ORSet, VersionVector}

import snapple.finagle.serialization.DataSerializer

import java.nio.ByteBuffer

case class OpHandler(host: String) {

  def handleOp(
    dataKind:    DataKind,
    elementKind: ElementKind,
    operation:   OpKind,
    buffer:      ByteBuffer
  ): (DataType ⇒ DataType) = dataKind match {
    case ORSetDataKind         ⇒ handleORSetOp(elementKind, operation, buffer)
    case VersionVectorDataKind ⇒ versionVectorOp
  }

  private[io] def handleORSetOp(
    elementKind: ElementKind,
    operation:   OpKind,
    buffer:      ByteBuffer
  ): (DataType ⇒ DataType) = operation match {
    case AddOpKind ⇒
      val element = DataSerializer.deserializeElement(buffer, elementKind)
      (dataType: DataType) ⇒ (dataType.asInstanceOf[ORSet[Any]] + (host, element))
    case RemoveOpKind ⇒
      val element = DataSerializer.deserializeElement(buffer, elementKind)
      (dataType: DataType) ⇒ (dataType.asInstanceOf[ORSet[Any]] - (host, element))
    case ClearOpKind ⇒
      (dataType: DataType) ⇒ dataType.asInstanceOf[ORSet[Any]].clear
  }

  private[io] lazy val versionVectorOp: (DataType ⇒ DataType) = (dataType: DataType) ⇒ {
    dataType.asInstanceOf[VersionVector] + host
  }

}
