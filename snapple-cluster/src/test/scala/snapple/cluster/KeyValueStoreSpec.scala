package snapple.cluster

import snapple.crdts.datatypes.{VersionVector, DataType, ORSet}

import snapple.thrift.io.{NoElementType, StringElementType}

import org.scalatest.{WordSpecLike, Matchers}

import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class KeyValueStoreSpec extends WordSpecLike with Matchers with ScalaFutures {

  "A KeyValueStore" must {

    "correctly merge" in {
      val host1 = "<host1>"
      val host2 = "<host2>"

      val orsetKey = "<orsetkey>"

      val orset1 = ORSet() + (host1, "hello")
      val orset2 = ORSet() + (host2, "goodbye")

      val vvKey = "<vvkey>"

      val vv1 = VersionVector() + host1
      val vv2 = VersionVector() + host2

      val otherLonerKey = "<otherlonerkey>"

      val other = Map(
        orsetKey -> KeyValueEntry(orset1, StringElementType),
        vvKey -> KeyValueEntry(vv1, NoElementType),
        otherLonerKey -> KeyValueEntry(orset1, StringElementType)
      )

      val thisLonerKey = "<thislonerkey>"

      val store = KeyValueStore()
      store.createEntry(orsetKey, KeyValueEntry(orset2, StringElementType))
      store.createEntry(vvKey, KeyValueEntry(vv2, NoElementType))
      store.createEntry(thisLonerKey, KeyValueEntry(orset2, StringElementType))

      store.merge(other)

      store.entry(orsetKey) match {
        case None => fail
        case Some(KeyValueEntry(d, e)) =>
          d should be (orset1.merge(orset2))
          e should be (StringElementType)
      }

      store.entry(vvKey) match {
        case None => fail
        case Some(KeyValueEntry(d, e)) =>
          d should be (vv1.merge(vv2))
          e should be (NoElementType)
      }

      store.entry(otherLonerKey) match {
        case None => fail
        case Some(KeyValueEntry(d, e)) =>
          d should be (orset1)
          e should be (StringElementType)
      }

      store.entry(thisLonerKey) match {
        case None => fail
        case Some(KeyValueEntry(d, e)) =>
          d should be (orset2)
          e should be (StringElementType)
      }
    }

    "correctly create entry" in {
      val key = "<key>"
      val store = KeyValueStore()
      val entry = KeyValueEntry(VersionVector(), NoElementType)

      store.createEntry(key, entry) should be (true)

      store.entry(key) should be (Some(entry))
      store.size should be (1)
      store.isEmpty should be (false)
    }

    "correctly work concurrently" in {
      val size = 1000
      val store = KeyValueStore()

      val v = KeyValueEntry(VersionVector(), NoElementType)
      val entries = Array.fill(size)(v).toSeq

      val future = Future.sequence(entries.zipWithIndex.map {
        case (entry, i) => Future { store.createEntry(i.toString, entry) }
      })

      whenReady(future) { _ =>
        val entries = store.entries

        for (i <- 0 until size) entries.get(i.toString) should be (Some(v))

        store.size should be (size)
      }
    }

    "correctly remove entry" in {
      val key = "<key>"
      val store = KeyValueStore()
      val entry = KeyValueEntry(VersionVector(), NoElementType)

      store.createEntry(key, entry) should be (true)
      store.removeEntry(key) should be (true)

      store.entry(key) should be (None)
      store.size should be (0)
      store.isEmpty should be (true)
    }

    "correctly read entry" in {
      val key = "<key>"
      val store = KeyValueStore()
      val entry = KeyValueEntry(VersionVector(), NoElementType)

      store.createEntry(key, entry) should be (true)

      store.entry(key) should be (Some(entry))
    }

    "correctly read entries" in {
      val k1 = "<key1>"
      val k2 = "<key2>"

      val store = KeyValueStore()
      val entry = KeyValueEntry(VersionVector(), NoElementType)

      store.createEntry(k1, entry) should be (true)
      store.createEntry(k2, entry) should be (true)

      store.entries should be (Map(k1 -> entry, k2 -> entry))
    }
  }
}
