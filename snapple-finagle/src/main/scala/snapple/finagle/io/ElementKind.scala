package snapple.finagle.io

object ElementKind {
  def apply(id: Int): ElementKind = id match {
    case 0 => NoElementKind
    case 1 => BooleanElementKind
    case 2 => ByteElementKind
    case 3 => IntElementKind
    case 4 => LongElementKind
    case 5 => DoubleElementKind
    case 6 => StringElementKind
    case _ => throw new IllegalArgumentException(s"Invalid element kind id $id")
  }
}

sealed trait ElementKind { val id: Int }
case object NoElementKind extends ElementKind { override val id: Int = 0 }
case object BooleanElementKind extends ElementKind { override val id: Int = 1 }
case object ByteElementKind extends ElementKind { override val id: Int = 2 }
case object IntElementKind extends ElementKind { override val id: Int = 3 }
case object LongElementKind extends ElementKind { override val id: Int = 4 }
case object DoubleElementKind extends ElementKind { override val id: Int = 5 }
case object StringElementKind extends ElementKind { override val id: Int = 6 }
