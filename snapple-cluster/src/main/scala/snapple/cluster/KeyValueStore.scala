package snapple.cluster

import snapple.crdts.datatypes.{DataType, ORSet, VersionVector}

import snapple.thrift.io.ThriftElementType

import java.util.concurrent.atomic.AtomicReference

case class KeyValueStore() {

  private val store: AtomicReference[Map[String, KeyValueEntry]] = new AtomicReference(Map.empty)

  def merge(other: Map[String, KeyValueEntry]): Unit = {
    val mergeFunction = (map: Map[String, KeyValueEntry]) ⇒ {
      val mergedMap: Map[String, KeyValueEntry] = (other.keySet ++ map.keySet).map {
        case key ⇒
          val value = ((map.get(key), other.get(key)): @unchecked) match {
            case (Some(KeyValueEntry(d1, e1)), None) ⇒ KeyValueEntry(d1, e1)
            case (None, Some(KeyValueEntry(d2, e2))) ⇒ KeyValueEntry(d2, e2)
            case (Some(KeyValueEntry(d1, e1)), Some(KeyValueEntry(d2, e2))) ⇒ d1 match {
              case v: VersionVector if d2.isInstanceOf[VersionVector] ⇒
                KeyValueEntry(v.merge(d2.asInstanceOf[VersionVector]), e1)
              case o: ORSet[_] if d2.isInstanceOf[ORSet[_]] ⇒
                KeyValueEntry(o.asInstanceOf[ORSet[Any]].merge(d2.asInstanceOf[ORSet[Any]]), e1)
              case _ ⇒ KeyValueEntry(d1, e1)
            }
          }

          (key → value)
      }.toMap

      Some(mergedMap)
    }

    casHelper(mergeFunction)
  }

  def createEntry(key: String, keyValueEntry: KeyValueEntry): Boolean = {
    val createEntryFunction = (map: Map[String, KeyValueEntry]) ⇒ {
      if (map.contains(key)) None
      else Some(map + (key → keyValueEntry))
    }

    casHelper(createEntryFunction)
  }

  def removeEntry(key: String): Boolean = {
    val removeEntryFunction = (map: Map[String, KeyValueEntry]) ⇒ {
      if (!map.contains(key)) None
      else Some(map - key)
    }

    casHelper(removeEntryFunction)
  }

  def entry(key: String): Option[KeyValueEntry] = store.get.get(key)

  def entries: Map[String, KeyValueEntry] = store.get

  def size: Int = entries.size

  def isEmpty: Boolean = entries.isEmpty

  private def casHelper(func: Map[String, KeyValueEntry] ⇒ Option[Map[String, KeyValueEntry]]): Boolean = {
    var success = false

    var aborted = false

    do {
      val current = store.get
      func(current) match {
        case Some(modified) ⇒ success = store.compareAndSet(current, modified)
        case None           ⇒ aborted = true
      }
    } while (!success && !aborted)

    success
  }

}
