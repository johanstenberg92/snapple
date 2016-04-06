package snapple.thrift.serialize

import snapple.thrift.io.TDataType

trait ThriftSerializable {

  def serialize: TDataType

}
