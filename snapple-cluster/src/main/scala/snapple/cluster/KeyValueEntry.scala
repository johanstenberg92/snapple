package snapple.cluster

import snapple.crdts.datatypes.{DataType, VersionVector, ORSet}

import snapple.thrift.io.{ThriftElementType, ThriftDataType, ORSetDataType, VersionVectorDataType}

import java.util.concurrent.atomic.AtomicReference

object KeyValueEntry {

  def unapply(keyValueEntry: KeyValueEntry): Option[(DataType, ThriftElementType)] = Some(keyValueEntry.get)

  def apply(dataType: DataType, elementType: ThriftElementType): KeyValueEntry = new KeyValueEntry(dataType, elementType)

}

class KeyValueEntry(dataType: DataType, val elementType: ThriftElementType) {

  lazy val thriftDataType: ThriftDataType = dataType match {
    case v: VersionVector => VersionVectorDataType
    case o: ORSet[_] => ORSetDataType
  }

  private val entry: AtomicReference[DataType] = new AtomicReference(dataType)

  def get: (DataType, ThriftElementType) = (entry.get, elementType)

  def modify(lambda: DataType => DataType): Unit = {
    var success = false

    do {
      val current = entry.get
      val modified = lambda(current)

      success = entry.compareAndSet(current, modified)
    } while (!success)
  }

}
