package snapple.thrift.io

object ThriftElementType {
  def apply(id: Int): ThriftElementType = id match {
    case 0 => NoElementType
    case 1 => BooleanElementType
    case 2 => ByteElementType
    case 3 => IntElementType
    case 4 => LongElementType
    case 5 => DoubleElementType
    case 6 => StringElementType
    case _ => throw new IllegalArgumentException(s"Invalid thrift element type id $id")
  }
}
sealed trait ThriftElementType { val id: Int }
case object NoElementType extends ThriftElementType { override val id: Int = 0 }
case object BooleanElementType extends ThriftElementType { override val id: Int = 1 }
case object ByteElementType extends ThriftElementType { override val id: Int = 2 }
case object IntElementType extends ThriftElementType { override val id: Int = 3 }
case object LongElementType extends ThriftElementType { override val id: Int = 4 }
case object DoubleElementType extends ThriftElementType { override val id: Int = 5 }
case object StringElementType extends ThriftElementType { override val id: Int = 6 }
