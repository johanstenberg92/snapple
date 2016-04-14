package snapple.thrift.io

object ThriftOpType {
  def apply(opType: String): ThriftOpType = opType match {
    case "Add" => ThriftAddOpType
    case "Remove" => ThriftRemoveOpType
    case "Clear" => ThriftClearOpType
  }
}

sealed trait ThriftOpType { val id: String }
case object ThriftAddOpType extends ThriftOpType { override val id: String = "Add" }
case object ThriftRemoveOpType extends ThriftOpType { override val id: String = "Remove" }
case object ThriftClearOpType extends ThriftOpType { override val id: String = "Clear" }
