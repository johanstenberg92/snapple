package snapple.tests.io

import snapple.cluster.SnappleServer

import snapple.cluster.utils.Configuration

import snapple.thrift.io._

import snapple.client.io.SnappleClient

import org.scalatest.{WordSpecLike, Matchers, BeforeAndAfter}

import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.ExecutionContext.Implicits.global

import java.util.UUID

class ClientSpecMultiJvmServer extends WordSpecLike {

  val identifier = "<identifier>"

  val TenSeconds = 10 * 1000

  val server = SnappleServer.run(Configuration(replicaIdentifier = identifier))

  Thread.sleep(TenSeconds)

  server.shutdown
}

class ClientSpecMultiJvmClient extends WordSpecLike with Matchers with ScalaFutures {

  val identifier = "<identifier>"

  val host = "localhost"

  "A client" must {
    "be able to connect, ping and disconnect" in {
      val client = SnappleClient(host)

      client.ping.futureValue should be (())

      client.disconnect
    }

    "be able to connect, create, read and disconnect" in {
      val client = SnappleClient(host)

      val key = UUID.randomUUID.toString

      client.createEntry(key, VersionVectorDataType, NoElementType).futureValue should be (true)

      val vv = client.entry(key).futureValue.getOrElse(fail).asVersionVector

      vv.versionAt(identifier) should not be (-1)

      client.disconnect
    }

    "be able to connect, create, modify, read and disconnect" in {
      val client = SnappleClient(host)

      val key = UUID.randomUUID.toString

      client.createEntry(key, ORSetDataType, LongElementType).futureValue should be (true)

      val element = 1337L

      client.modifyEntry(key, AddOpType, Some(element)).futureValue should be (true)

      val orset = client.entry(key).futureValue.getOrElse(fail).asORSet

      orset.elements should be (Set(element))

      client.disconnect
    }

    "be able to connect, read, modify and disconnect" in {
      val client = SnappleClient(host)

      val key = UUID.randomUUID.toString

      client.entry(key).futureValue.isEmpty should be (true)
      client.modifyEntry(key, AddOpType, Some(1337L)).futureValue should be (false)

      client.disconnect
    }

    "be able to connect, create, remove, read and disconnect" in {
      val client = SnappleClient(host)

      val key = UUID.randomUUID.toString

      client.createEntry(key, VersionVectorDataType, NoElementType).futureValue should be (true)
      client.removeEntry(key).futureValue should be (true)
      client.removeEntry(key).futureValue should be (false)

      client.entry(key).futureValue.isEmpty should be (true)

      client.disconnect
    }

    "be able to connect, create, disconnect, connect, read and disconnect" in {
      var client = SnappleClient(host)

      val key = UUID.randomUUID.toString

      client.createEntry(key, VersionVectorDataType, NoElementType).futureValue should be (true)

      client.disconnect

      client = SnappleClient(host)

      val vv = client.entry(key).futureValue.getOrElse(fail).asVersionVector

      vv.versionAt(identifier) should not be (-1)

      client.disconnect
    }
  }
}
