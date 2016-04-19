package snapple.tests.io.client

import snapple.cluster.SnappleServer

import snapple.cluster.utils.Configuration

import snapple.finagle.io._
import snapple.finagle.utils.FinagleUtils._

import snapple.client.io.SnappleClient

import org.scalatest.{WordSpecLike, Matchers, BeforeAndAfter}

import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import java.util.UUID

class ClientRotateSpec extends WordSpecLike with Matchers with ScalaFutures with BeforeAndAfter {

  muteLogs

  var id1 = "<id1>"
  val host1 = "localhost"
  val port1 = SnappleClient.DefaultPort

  var id2 = "<id2>"
  val host2 = "localhost"
  val port2 = SnappleClient.DefaultPort + 1

  var server1: SnappleServer = null
  var server2: SnappleServer = null

  before {
    server1 = SnappleServer.run(Configuration(port = port1, replicaIdentifier = id1))
    server2 = SnappleServer.run(Configuration(port = port2, replicaIdentifier = id2))
  }

  after {
    try { server1.shutdown } catch { case _: Throwable => }
    try { server2.shutdown } catch { case _: Throwable => }
  }

  "A client" must {
    "change replica seamlessly if one is down" in {
      val key = UUID.randomUUID.toString

      val c1 = SnappleClient.singleHost(host1, port1)

      c1.createEntry(key, VersionVectorDataKind, NoElementKind).futureValue should be (true)
      c1.modifyEntry(key, AddOpKind, None).futureValue should be (true)

      c1.disconnect

      val c2 = SnappleClient.singleHost(host2, port2)

      c2.createEntry(key, VersionVectorDataKind, NoElementKind).futureValue should be (true)
      c2.modifyEntry(key, AddOpKind, None).futureValue should be (true)

      c2.disconnect

      val client = SnappleClient(Seq((host1, port1), (host2, port2)))

      val vv1 = client.entry(key).futureValue.getOrElse(fail).asVersionVector

      val h1 = "host1"
      val h2 = "host2"
      val hostConnectedTo = if (vv1.versionAt(id1) != 0) h1 else h2

      if (hostConnectedTo != h1) {
        val idt = id1
        id1 = id2
        id2 = idt

        val st = server1
        server1 = server2
        server2 = st
      }

      vv1.versionAt(id1) should not be (0)
      vv1.versionAt(id2) should be (0)

      server1.shutdown

      val vv2 = client.entry(key).futureValue.getOrElse(fail).asVersionVector

      vv2.versionAt(id1) should be (0)
      vv2.versionAt(id2) should not be (0)

      server2.shutdown

      client.disconnect
    }
  }
}
