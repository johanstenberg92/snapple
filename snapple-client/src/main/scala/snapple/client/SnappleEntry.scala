package snapple.client

import snapple.crdts.datatypes.{DataType, ORSet, VersionVector}

import snapple.thrift.io._

case class SnappleEntry(dataType: DataType, elementType: ThriftElementType) {

  lazy val thriftDataType: ThriftDataType = dataType match {
    case v: VersionVector => VersionVectorDataType
    case o: ORSet[_] => ORSetDataType
  }

  def asVersionVector: VersionVector = dataType.asInstanceOf[VersionVector]

  def asORSet: ORSet[Any] = dataType.asInstanceOf[ORSet[Any]]

}
