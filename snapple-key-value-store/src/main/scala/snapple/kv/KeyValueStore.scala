package snapple.kv

import snapple.kv.datatypes.DataType

import java.util.concurrent.atomic.AtomicReference

case class KeyValueStore() {

  private val store: AtomicReference[Map[String, DataType]] = new AtomicReference()

  def addValue(key: String, dataType: DataType): Unit = ???

  def removeValue(key: String): Boolean = ???

  def getValue(key: String): Option[DataType] = ???
}
