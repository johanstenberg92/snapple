package snapple.finagle.io

object DataKind {
  def apply(dataKind: String): DataKind = dataKind match {
    case "ORSet" => ORSetDataKind
    case "VersionVector" => VersionVectorDataKind
    case _ => throw new IllegalArgumentException(s"Invalid data kind $dataKind")
  }
}

sealed trait DataKind { val id: String }
case object ORSetDataKind extends DataKind { override val id: String = "ORSet" }
case object VersionVectorDataKind extends DataKind { override val id: String = "VersionVector" }
