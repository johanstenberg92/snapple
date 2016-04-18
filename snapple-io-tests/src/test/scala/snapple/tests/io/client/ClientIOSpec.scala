package snapple.tests.io.client

import snapple.cluster.SnappleServer

import snapple.cluster.utils.Configuration

import snapple.finagle.io._

import snapple.client.io.SnappleClient

import org.scalatest.{WordSpecLike, Matchers, BeforeAndAfterAll}

import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import java.util.UUID

class ClientIOSpec extends WordSpecLike with Matchers with ScalaFutures with BeforeAndAfterAll {

  val identifier = "<identifier>"

  val host = "localhost"

  var server: SnappleServer = null

  override def beforeAll() {
    server = SnappleServer.run(Configuration(replicaIdentifier = identifier))
  }

  override def afterAll() {
    server.shutdown
  }

  "A client" must {
    "be able to connect, ping and disconnect" in {
      val client = SnappleClient.singleHost(host)

      client.ping.futureValue should be (())

      client.disconnect
    }

    "be able to connect, create, read and disconnect" in {
      val client = SnappleClient.singleHost(host)

      val key = UUID.randomUUID.toString

      client.createEntry(key, VersionVectorDataKind, NoElementKind).futureValue should be (true)

      val vv = client.entry(key).futureValue.getOrElse(fail).asVersionVector

      vv.versionAt(identifier) should not be (0)

      client.disconnect
    }

    "be able to connect, create, modify, read and disconnect" in {
      val client = SnappleClient.singleHost(host)

      val key = UUID.randomUUID.toString

      client.createEntry(key, ORSetDataKind, LongElementKind).futureValue should be (true)

      val element = 1337L

      client.modifyEntry(key, AddOpKind, Some(element)).futureValue should be (true)

      val orset = client.entry(key).futureValue.getOrElse(fail).asORSet

      orset.elements should be (Set(element))

      client.disconnect
    }

    "be able to connect, read, modify and disconnect" in {
      val client = SnappleClient.singleHost(host)

      val key = UUID.randomUUID.toString

      client.entry(key).futureValue.isEmpty should be (true)
      client.modifyEntry(key, AddOpKind, Some(1337L)).futureValue should be (false)

      client.disconnect
    }

    "be able to connect, create, remove, read and disconnect" in {
      val client = SnappleClient.singleHost(host)

      val key = UUID.randomUUID.toString

      client.createEntry(key, VersionVectorDataKind, NoElementKind).futureValue should be (true)
      client.removeEntry(key).futureValue should be (true)
      client.removeEntry(key).futureValue should be (false)

      client.entry(key).futureValue.isEmpty should be (true)

      client.disconnect
    }

    "be able to connect, create, disconnect, connect, read and disconnect" in {
      var client = SnappleClient.singleHost(host)

      val key = UUID.randomUUID.toString

      client.createEntry(key, VersionVectorDataKind, NoElementKind).futureValue should be (true)

      client.disconnect

      client = SnappleClient.singleHost(host)

      val vv = client.entry(key).futureValue.getOrElse(fail).asVersionVector

      vv.versionAt(identifier) should not be (0)

      client.disconnect
    }

    "be able to connect, create string orset, modify, read and disconnect" in {
      val client = SnappleClient.singleHost(host)

      val key = UUID.randomUUID.toString

      client.createEntry(key, ORSetDataKind, StringElementKind).futureValue should be (true)

      val element = "Yo Whats UPP"

      client.modifyEntry(key, AddOpKind, Some(element)).futureValue should be (true)

      val orset = client.entry(key).futureValue.getOrElse(fail).asORSet

      orset.elements should be (Set(element))

      client.disconnect
    }

    "be able to use two clients at the same time" in {
      val c1 = SnappleClient.singleHost(host)
      val c2 = SnappleClient.singleHost(host)

      val k1 = UUID.randomUUID.toString
      val k2 = UUID.randomUUID.toString

      val f1 = c1.createEntry(k1, ORSetDataKind, StringElementKind)
      val f2 = c2.createEntry(k2, ORSetDataKind, StringElementKind)

      f1.futureValue should be (true)
      f2.futureValue should be (true)

      c1.disconnect
      c2.disconnect
    }

    "be able to call multiple methods simultaneously with client" in {
      val size = 50
      val client = SnappleClient.singleHost(host)

      val future = Future.sequence((0 until size).toSeq.map(_ => client.ping))

      future.futureValue should be ((0 until size).map(_ => ()))
    }
  }
}
