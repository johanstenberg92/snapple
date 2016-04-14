package snapple.thrift.io

object ThriftDataType {
  def apply(dataType: String): ThriftDataType = dataType match {
    case "ORSet" => ORSetDataType
    case "VersionVector" => VersionVectorDataType
    case _ => throw new IllegalArgumentException(s"Invalid thrift data type $dataType")
  }
}

sealed trait ThriftDataType { val id: String }
case object ORSetDataType extends ThriftDataType { override val id: String = "ORSet" }
case object VersionVectorDataType extends ThriftDataType { override val id: String = "VersionVector" }
