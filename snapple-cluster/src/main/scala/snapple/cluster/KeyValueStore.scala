package snapple.kv

import snapple.kv.datatypes.DataType

import java.util.concurrent.atomic.AtomicReference

case class KeyValueStore() {

  private val store: AtomicReference[Map[String, DataType]] = new AtomicReference()

  def createEntry(key: String, dataType: DataType): Boolean = ???

  def removeEntry(key: String): Boolean = ???

  def getEntry(key: String): Option[DataType] = ???
}
