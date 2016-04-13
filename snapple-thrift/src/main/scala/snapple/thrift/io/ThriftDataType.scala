package snapple.thrift.io

object ThriftDataType {
  def apply(dataType: String): ThriftDataType = id match {
    case "ORSet" => ORSetDataType
    case "VersionVector" => VersionVectorDataType
    case _ => throw new IllegalArgumentException(s"Invalid thrift data type $dataType")
  }
}

sealed trait ThriftDataType
case object ORSetDataType extends ThriftDataType
case object VersionVectorDataType extends ThriftDataType
