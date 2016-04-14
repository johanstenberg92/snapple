package snapple.cluster

import snapple.crdts.datatypes.{DataType, ORSet, VersionVector}

import snapple.thrift.io.ThriftElementType

import java.util.concurrent.atomic.AtomicReference

case class KeyValueStore() {

  private val store: AtomicReference[Map[String, (DataType, ThriftElementType)]] = new AtomicReference()

  def merge(other: Map[String, (DataType, ThriftElementType)]): Unit = {
    val mergeFunction = (map: Map[String, (DataType, ThriftElementType)]) => {
      val mergedMap: Map[String, (DataType, ThriftElementType)] = (other.keySet ++ map.keySet).map {
        case key =>
          val value = ((map.get(key), other.get(key)): @unchecked) match {
            case (Some((d1, e1)), None) => (d1, e1)
            case (None, Some((d2, e2))) => (d2, e2)
            case (Some((d1, e1)), Some((d2, e2))) => d1 match {
              case v: VersionVector if d2.isInstanceOf[VersionVector] => (v.merge(d2.asInstanceOf[VersionVector]), e1)
              case o: ORSet[_] if d2.isInstanceOf[ORSet[_]] => (o.asInstanceOf[ORSet[Any]].merge(d2.asInstanceOf[ORSet[Any]]), e1)
              case _ => (d1, e1)
            }
          }

          (key -> value)
      }.toMap

      Some(mergedMap)
    }

    casHelper(mergeFunction)
  }


  def createEntry(key: String, dataType: DataType, elementType: ThriftElementType): Boolean = {
    val createEntryFunction = (map: Map[String, (DataType, ThriftElementType)]) => {
      if (map.contains(key)) None
      else Some(map + (key -> (dataType, elementType)))
    }

    casHelper(createEntryFunction)
  }

  def removeEntry(key: String): Boolean = {
    val removeEntryFunction = (map: Map[String, (DataType, ThriftElementType)]) => {
      if (!map.contains(key)) None
      else Some(map - key)
    }

    casHelper(removeEntryFunction)
  }

  def entry(key: String): Option[(DataType, ThriftElementType)] = store.get.get(key)

  def entries: Map[String, (DataType, ThriftElementType)] = store.get

  private def casHelper(func: Map[String, (DataType, ThriftElementType)] => Option[Map[String, (DataType, ThriftElementType)]]): Boolean = {
    var success = false

    var aborted = false

    do {
      val current = store.get
      func(current) match {
        case Some(modified) => store.compareAndSet(current, modified)
        case None => aborted = true
      }
    } while (!success && !aborted)

    success
  }

}
