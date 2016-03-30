package snapple.crdts

trait DataType {

  type T <: DataType

  def merge(that: T): T

}
