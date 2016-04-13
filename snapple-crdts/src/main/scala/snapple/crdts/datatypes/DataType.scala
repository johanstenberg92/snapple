package snapple.crdts.datatypes

trait DataType {

  type S <: DataType

  def merge(that: S): S

}
