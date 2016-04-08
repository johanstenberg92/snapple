package snapple.cluster

import snapple.crdts.datatypes.DataType

import java.util.concurrent.atomic.AtomicReference

case class KeyValueStore() {

  private val store: AtomicReference[Map[String, DataType]] = new AtomicReference()

  def merge(other: Map[String, DataType]): Unit = {
    ???
  }

  def createEntry(key: String, dataType: DataType): Boolean = ???

  def removeEntry(key: String): Boolean = ???

  def entry(key: String): Option[DataType] = ???

  def entries: Map[String, DataType] = store.get

}
