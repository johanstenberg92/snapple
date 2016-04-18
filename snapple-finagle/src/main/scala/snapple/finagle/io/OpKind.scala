package snapple.finagle.io

object OpKind {
  def apply(opKind: String): OpKind = opKind match {
    case "Add" => AddOpKind
    case "Remove" => RemoveOpKind
    case "Clear" => ClearOpKind
    case _ => throw new IllegalArgumentException(s"Invalid op kind $opKind")
  }
}

sealed trait OpKind { val id: String }
case object AddOpKind extends OpKind { override val id: String = "Add" }
case object RemoveOpKind extends OpKind { override val id: String = "Remove" }
case object ClearOpKind extends OpKind { override val id: String = "Clear" }
