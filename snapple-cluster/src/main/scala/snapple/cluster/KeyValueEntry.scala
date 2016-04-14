package snapple.cluster

import snapple.crdts.datatypes.DataType

import snapple.thrift.io.ThriftElementType

import java.util.concurrent.atomic.AtomicReference

case class KeyValueEntry(private val dataType: DataType, private val elementType: ThriftElementType) {

  private val entry: AtomicReference[(DataType, ThriftElementType)] = new AtomicReference((dataType, elementType))

  def get: (DataType, ThriftElementType) = entry.get

}
