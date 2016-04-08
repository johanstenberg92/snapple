package snapple.thrift.serialization

import snapple.thrift.io.TDataType

import snapple.crdts.datatypes.DataType

object DataSerializer {

  def serialize(dataType: DataType): TDataType = ???

  def deserialize(dataType: TDataType): DataType = ???

}
