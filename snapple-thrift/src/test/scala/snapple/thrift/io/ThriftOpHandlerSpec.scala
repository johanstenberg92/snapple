package snapple.thrift.io

import snapple.crdts.datatypes.{ORSet, VersionVector}

import org.scalatest.{WordSpecLike, Matchers}

import java.nio.ByteBuffer

class ThriftOpHandlerSpec extends WordSpecLike with Matchers {

  val host = "<host>"

  val emptyBB = ByteBuffer.allocate(0)

  val opHandler = ThriftOpHandler(host)

  "The ThriftOpHandler" must {

    "handle version vector op" in {
      val op = opHandler.handleOp(VersionVectorDataType, NoElementType, AddOpType, emptyBB)

      val vv = op(VersionVector()).asInstanceOf[VersionVector]

      vv.size should be (1)
      vv.versionAt(host) should not be (-1)
    }

    "handle or set add op" in {
      val target = 1337L

      val bb = ByteBuffer.allocate(8)
      bb.putLong(target)
      val op = opHandler.handleOp(ORSetDataType, LongElementType, AddOpType, bb)
      val orset = op(ORSet()).asInstanceOf[ORSet[Any]]

      orset.elements should be (Set(target))
    }

    "handle or set remove op" in {
      val target = 1337L

      val orset = ORSet() + (host, target)

      orset.elements should be (Set(target))

      val bb = ByteBuffer.allocate(8)
      bb.putLong(target)
      val op = opHandler.handleOp(ORSetDataType, LongElementType, RemoveOpType, bb)
      val orset2 = op(orset).asInstanceOf[ORSet[Any]]

      orset2.elements should be (Set.empty)
    }

    "handle or set clear op" in {
      val orset = ORSet() + (host, 1337L)

      val op = opHandler.handleOp(ORSetDataType, LongElementType, ClearOpType, emptyBB)
      val orset2 = op(orset).asInstanceOf[ORSet[Any]]

      orset2.elements should be (Set.empty)
    }
  }

}
