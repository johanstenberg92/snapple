package snapple.tests.io.replica

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

class ReplicaIOSpec extends WordSpecLike with Matchers with ScalaFutures with BeforeAndAfter {

  muteLogs

  val host = "localhost"

  val id1 = "<id1>"
  val id2 = "<id2>"
  val id3 = "<id3>"
  val id4 = "<id4>"

  val port1 = SnappleClient.DefaultPort
  val port2 = SnappleClient.DefaultPort + 1
  val port3 = SnappleClient.DefaultPort + 2
  val port4 = SnappleClient.DefaultPort + 3

  var server1: SnappleServer = null
  var server2: SnappleServer = null
  var server3: SnappleServer = null

  val replicas = Seq((host, port1), (host, port2), (host, port3))

  before {
    server1 = SnappleServer.run(Configuration(port = port1, replicaIdentifier = id1, replicaAddresses = replicas))
    server2 = SnappleServer.run(Configuration(port = port2, replicaIdentifier = id2, replicaAddresses = replicas))
    server3 = SnappleServer.run(Configuration(port = port3, replicaIdentifier = id3, replicaAddresses = replicas))
  }

  after {
    try { server1.shutdown } catch { case _: Throwable ⇒ }
    try { server2.shutdown } catch { case _: Throwable ⇒ }
    try { server3.shutdown } catch { case _: Throwable ⇒ }
  }

  "A replica" must {
    "be able to propagate changes to other replicas" in {
      val client = SnappleClient(replicas)

      val key = UUID.randomUUID.toString

      client.createEntry(key, ORSetDataKind, LongElementKind).futureValue should be(true)
      client.modifyEntry(key, AddOpKind, Some(1337L)).futureValue should be(true)

      client.disconnect

      server1.propagator.propagate.futureValue
      server2.propagator.propagate.futureValue
      server3.propagator.propagate.futureValue

      val map1 = server1.shutdown
      val map2 = server2.shutdown
      val map3 = server3.shutdown

      map1 should be(map2)
      map1 should be(map3)

      map2 should be(map3)
    }

    "be able to propagate with old service coming back to life" in {
      server3.shutdown

      val client = SnappleClient(Seq((host, port1), (host, port2)))

      val key = UUID.randomUUID.toString

      client.createEntry(key, ORSetDataKind, LongElementKind).futureValue should be(true)
      client.modifyEntry(key, AddOpKind, Some(1337L)).futureValue should be(true)

      client.disconnect

      whenReady(server1.propagator.propagate.failed) { _ =>
        whenReady(server2.propagator.propagate.failed) { _ =>
          server3 = SnappleServer.run(Configuration(port = port3, replicaIdentifier = id3, replicaAddresses = replicas))

          server1.propagator.propagate.futureValue
          server2.propagator.propagate.futureValue
          server3.propagator.propagate.futureValue

          val map1 = server1.shutdown
          val map2 = server2.shutdown
          val map3 = server3.shutdown

          map1 should be(map2)
          map1 should be(map3)

          map2 should be(map3)
        }
      }

    }

    "be able to propagate with new service" in {
      val client = SnappleClient(replicas)

      val key = UUID.randomUUID.toString

      client.createEntry(key, ORSetDataKind, LongElementKind).futureValue should be(true)
      client.modifyEntry(key, AddOpKind, Some(1337L)).futureValue should be(true)

      client.disconnect

      var server4: SnappleServer = null

      try {
        server4 = SnappleServer.run(Configuration(port = port4, replicaIdentifier = id4, replicaAddresses = replicas))

        server4.propagator.propagate.futureValue

        server1.propagator.propagate.futureValue
        server2.propagator.propagate.futureValue
        server3.propagator.propagate.futureValue
        server4.propagator.propagate.futureValue

        val map1 = server1.shutdown
        val map2 = server2.shutdown
        val map3 = server3.shutdown
        val map4 = server4.shutdown

        map1 should be(map2)
        map1 should be(map3)
        map1 should be(map4)

        map2 should be(map3)
        map2 should be(map4)

        map3 should be(map4)
      } finally {
        try { server4.shutdown } catch { case _: Throwable ⇒ }
      }
    }
  }
}
