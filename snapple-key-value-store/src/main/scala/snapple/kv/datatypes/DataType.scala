package snapple.kv.datatypes

import snapple.kv.io.TDataType

trait DataType {

  type S <: DataType

  def merge(that: S): S

}

