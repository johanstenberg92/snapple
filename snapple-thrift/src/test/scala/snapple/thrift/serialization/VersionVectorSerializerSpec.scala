package snapple.thrift.serialization

import snapple.thrift.io.NoElementType

import snapple.crdts.datatypes.VersionVector

import org.scalatest.{WordSpecLike, Matchers}

import java.util.UUID

class VersionVectorSerializerSpec extends WordSpecLike with Matchers {

  val node1 = UUID.randomUUID.toString
  val node2 = UUID.randomUUID.toString
  val node3 = UUID.randomUUID.toString
  val node4 = UUID.randomUUID.toString

  def serializeAndDeserialize(versionVector: VersionVector): VersionVector = {
    val serialized = VersionVectorSerializer.serialize(versionVector)
    VersionVectorSerializer.deserialize(serialized)
  }

  "The DataSerializer" must {

    "correctly serialize and deserialize a version vector" in {
      val vv1_1 = VersionVector()
      val vv2_1 = vv1_1 + node1
      val vv3_1 = vv2_1 + node2
      val vv4_1 = vv3_1 + node1

      DataSerializer.deserialize(DataSerializer.serialize(vv4_1)) should be ((vv4_1, NoElementType))
    }

  }

  "A VersionVector" must {

    "be correctly serialized and deserialized" in {
      val vv = VersionVector()

      serializeAndDeserialize(vv) should be (vv)
    }

    "be correctly serialized and deserialized 2" in {
      val vv1_1 = VersionVector()
      val vv2_1 = vv1_1 + node1
      val vv3_1 = vv2_1 + node2
      val vv4_1 = vv3_1 + node1

      serializeAndDeserialize(vv4_1) should be (vv4_1)
    }

    "be correctly serialized and deserialized 3" in {
      val vv1_1 = VersionVector()
      val vv2_1 = vv1_1 + node1
      val vv3_1 = vv2_1 + node2
      val vv4_1 = vv3_1 + node1
      val vv5_1 = vv4_1 + node4
      val vv6_1 = vv5_1 + node3
      val vv7_1 = vv6_1 + node4
      val vv8_1 = vv7_1 + node1

      serializeAndDeserialize(vv8_1) should be (vv8_1)
    }
  }
}
