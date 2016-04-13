package snapple.thrift.io

object ThriftArgParser {

  def parseCreateEntry(dataType: String, elementType: Int): Option[DataType] = ThriftDataType(dataType) match {
    case ORSetDataType         ⇒
    case VersionVectorDataType ⇒
  }

  private def parseCreateORSetEntry(elementType: Int): Option[DataType] = ThriftElementType(elementType) match {
    case BooleanElementType => ORSet[Boolean]
    case ByteElementType =>
    case IntElementType =>
    case LongElementType =>
    case DoubleElementType =>
    case StringElementType =>
    case NoElementType => throw new IllegalArgumentException(s"ORSets can't have $NoElemnentType")
  }
}
