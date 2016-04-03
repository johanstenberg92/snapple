package snapple.kv.io

trait ThriftSerializable {

  def serialize: TDataType

}
