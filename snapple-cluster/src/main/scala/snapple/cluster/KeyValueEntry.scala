package snapple.cluster

import snapple.crdts.datatypes.{DataType, VersionVector, ORSet}

import snapple.finagle.io.{ElementKind, DataKind, ORSetDataKind, VersionVectorDataKind}

import java.util.concurrent.atomic.AtomicReference

object KeyValueEntry {

  def unapply(keyValueEntry: KeyValueEntry): Option[(DataType, ElementKind)] = Some(keyValueEntry.get)

  def apply(dataType: DataType, elementKind: ElementKind): KeyValueEntry = new KeyValueEntry(dataType, elementKind)

}

class KeyValueEntry(dataType: DataType, val elementKind: ElementKind) {

  lazy val dataKind: DataKind = dataType match {
    case v: VersionVector => VersionVectorDataKind
    case o: ORSet[_] => ORSetDataKind
  }

  private val entry: AtomicReference[DataType] = new AtomicReference(dataType)

  def get: (DataType, ElementKind) = (entry.get, elementKind)

  def modify(lambda: DataType => DataType): Unit = {
    var success = false

    do {
      val current = entry.get
      val modified = lambda(current)

      success = entry.compareAndSet(current, modified)
    } while (!success)
  }

  override def toString: String = {
    val (d, e) = get
    s"KeyValueEntry($d, $e)"
  }

}
