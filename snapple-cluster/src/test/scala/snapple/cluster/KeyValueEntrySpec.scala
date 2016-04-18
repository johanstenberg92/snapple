package snapple.cluster

import snapple.crdts.datatypes.{VersionVector, DataType, ORSet}

import snapple.finagle.io._

import org.scalatest.{WordSpecLike, Matchers}

import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class KeyValueEntrySpec extends WordSpecLike with Matchers with ScalaFutures {

  "A KeyValueEntry" must {

    "be correctly modified" in {
      val host = "<host>"

      val entry = KeyValueEntry(VersionVector(), NoElementKind)

      val op = (d: DataType) => d.asInstanceOf[VersionVector] + host

      entry.modify(op)

      val (dataType, elementType) = entry.get

      val vv = dataType.asInstanceOf[VersionVector]

      vv.size should be (1)
      vv.versionAt(host) should not be (0)
    }

    "be correctly modified concurrently" in {
      val size = 1000
      val entry = KeyValueEntry(VersionVector(), NoElementKind)

      val ops = Array.ofDim[(DataType => DataType)](size)

      for (i <- 0 until size) ops(i) = (d: DataType) => d.asInstanceOf[VersionVector] + i.toString

      val future = Future.sequence(ops.map(op => Future { entry.modify(op) }).toSeq)

      whenReady(future) { _ =>
        val (dataType, elementType) = entry.get

        val vv = dataType.asInstanceOf[VersionVector]

        vv.size should be (size)

        for (i <- 0 until size) {
          vv.versionAt(i.toString) should not be (0)
        }
      }
    }

    "have correct data kind" in {
      KeyValueEntry(VersionVector(), NoElementKind).dataKind should be (VersionVectorDataKind)
      KeyValueEntry(ORSet(), LongElementKind).dataKind should be (ORSetDataKind)
    }

  }
}
