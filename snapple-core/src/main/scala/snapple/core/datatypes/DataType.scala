package snapple.core.datatypes

trait DataType {

  type S <: DataType

  def merge(that: S): S

}

sealed trait DataTypeKind

case object ORSetKind extends DataTypeKind

case object VersionVectorKind extends DataTypeKind
