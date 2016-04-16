package snapple.thrift.io

object ThriftOpType {
  def apply(opType: String): ThriftOpType = opType match {
    case "Add" => AddOpType
    case "Remove" => RemoveOpType
    case "Clear" => ClearOpType
  }
}

sealed trait ThriftOpType { val id: String }
case object AddOpType extends ThriftOpType { override val id: String = "Add" }
case object RemoveOpType extends ThriftOpType { override val id: String = "Remove" }
case object ClearOpType extends ThriftOpType { override val id: String = "Clear" }
