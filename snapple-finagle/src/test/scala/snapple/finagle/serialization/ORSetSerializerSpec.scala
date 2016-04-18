package snapple.finagle.serialization

import snapple.finagle.io._

import org.scalatest.{WordSpecLike, Matchers}

import snapple.crdts.datatypes.ORSet

import java.util.UUID

class ORSetSerializerSpec extends WordSpecLike with Matchers {

  val node1 = UUID.randomUUID.toString
  val node2 = UUID.randomUUID.toString

  val nodeA = UUID.randomUUID.toString
  val nodeB = UUID.randomUUID.toString
  val nodeC = UUID.randomUUID.toString
  val nodeD = UUID.randomUUID.toString
  val nodeE = UUID.randomUUID.toString
  val nodeF = UUID.randomUUID.toString
  val nodeG = UUID.randomUUID.toString
  val nodeH = UUID.randomUUID.toString

  val user1 = """{"username":"allen","password":"iverson"}"""
  val user2 = """{"username":"magic","password":"johnson"}"""
  val user3 = """{"username":"blake","password":"griffin"}"""
  val user4 = """{"username":"steph","password":"curry"}"""

  def serializeAndDeserialize[T](orset: ORSet[T], elementKind: ElementKind): (ORSet[Any], ElementKind) = {
    val serialized = ORSetSerializer.serialize(orset, elementKind)
    ORSetSerializer.deserialize(serialized)
  }

  "The DataSerializer" must {

    "correctly serialize and deserialize an orset" in {
      val c1 = ORSet()

      val c2 = c1 + (node1, user1)
      val c3 = c2 + (node1, user2)

      val c4 = c3 + (node1, user4)
      val c5 = c4 + (node1, user3)

      DataSerializer.deserialize(DataSerializer.serialize(c5, StringElementKind)) should be ((c5, StringElementKind))
    }

  }

  "An ORSet" must {

    "be correctly serialized and deserialized with strings" in {
      val c1 = ORSet()

      val c2 = c1 + (node1, user1)
      val c3 = c2 + (node1, user2)

      val c4 = c3 + (node1, user4)
      val c5 = c4 + (node1, user3)

      serializeAndDeserialize(c5, StringElementKind) should be (c5, StringElementKind)
    }

    "be correctly serialized and deserialized with strings 2" in {
      val c1 = ORSet()

      val c2 = c1 + (node1, user1)
      val c3 = c2 + (node1, user2)

      val c4 = c3 - (node1, user2)
      val c5 = c4 - (node1, user1)

      val c6 = c3.merge(c5)

      serializeAndDeserialize(c6, StringElementKind) should be (c6, StringElementKind)
    }

    "be correctly serialized and deserialized with strings 3" in {
      val c1 = ORSet()

      val c2 = c1 + (node1, user1)
      val c3 = c2 + (node1, user2)
      val c4 = c3 - (node1, user1)

      val c5 = c4 + (node1, user1)
      val c6 = c5 + (node1, user2)

      val c7 = c6 - (node1, user1)
      val c8 = c7 + (node1, user2)
      val c9 = c8 - (node1, user1)

      serializeAndDeserialize(c9, StringElementKind) should be (c9, StringElementKind)
    }

    "be correctly serialized and deserialized with strings 4" in {
      val c1 = ORSet() + (node1, user1) + (node1, user2) + (node1, user3)
      val c2 = c1 + (node2, user1) - (node2, user2) - (node2, user3)
      val c3 = c1 + (node1, user4) - (node1, user3) + (node1, user2)
      val c4 = c3 merge c2

      serializeAndDeserialize(c4, StringElementKind) should be (c4, StringElementKind)
    }

    "be correctly serialized and deserialized with booleans" in {
      val (v1, v2, v3, v4) = (true, false, true, false)
      val c1 = ORSet() + (node1, v1) + (node1, v2) + (node1, v3)
      val c2 = c1 + (node2, v1) - (node2, v2) - (node2, v3)
      val c3 = c1 + (node1, v4) - (node1, v3) + (node1, v2)
      val c4 = c3 merge c2

      serializeAndDeserialize(c4, BooleanElementKind) should be (c4, BooleanElementKind)
    }

    "be correctly serialized and deserialized with bytes" in {
      val (v1, v2, v3, v4) = (1.toByte, 50.toByte, 51.toByte, 50.toByte)
      val c1 = ORSet() + (node1, v1) + (node1, v2) + (node1, v3)
      val c2 = c1 + (node2, v1) - (node2, v2) - (node2, v3)
      val c3 = c1 + (node1, v4) - (node1, v3) + (node1, v2)
      val c4 = c3 merge c2

      serializeAndDeserialize(c4, ByteElementKind) should be (c4, ByteElementKind)
    }

    "be correctly serialized and deserialized with ints" in {
      val (v1, v2, v3, v4) = (1, 2, 3, 1337)
      val c1 = ORSet() + (node1, v1) + (node1, v2) + (node1, v3)
      val c2 = c1 + (node2, v1) - (node2, v2) - (node2, v3)
      val c3 = c1 + (node1, v4) - (node1, v3) + (node1, v2)
      val c4 = c3 merge c2

      serializeAndDeserialize(c4, IntElementKind) should be (c4, IntElementKind)
    }

    "be correctly serialized and deserialized with longs" in {
      val (v1, v2, v3, v4) = (1L, 2L, 3L, Long.MaxValue)
      val c1 = ORSet() + (node1, v1) + (node1, v2) + (node1, v3)
      val c2 = c1 + (node2, v1) - (node2, v2) - (node2, v3)
      val c3 = c1 + (node1, v4) - (node1, v3) + (node1, v2)
      val c4 = c3 merge c2

      serializeAndDeserialize(c4, LongElementKind) should be (c4, LongElementKind)
    }

    "be correctly serialized and deserialized with doubles" in {
      val (v1, v2, v3, v4) = (1.0, 2.0, 3.0, 1337.0)
      val c1 = ORSet() + (node1, v1) + (node1, v2) + (node1, v3)
      val c2 = c1 + (node2, v1) - (node2, v2) - (node2, v3)
      val c3 = c1 + (node1, v4) - (node1, v3) + (node1, v2)
      val c4 = c3 merge c2

      serializeAndDeserialize(c4, DoubleElementKind) should be (c4, DoubleElementKind)
    }

  }

}
