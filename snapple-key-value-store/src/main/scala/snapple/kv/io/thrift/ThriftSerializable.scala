package snapple.kv.io.thrift

trait ThriftSerializable {

  def serialize: TDataType

}
