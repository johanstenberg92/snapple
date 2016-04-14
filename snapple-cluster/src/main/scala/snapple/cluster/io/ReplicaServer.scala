package snapple.cluster.io

import snapple.thrift.io._

import snapple.thrift.serialization.DataSerializer

import snapple.cluster.KeyValueStore

import grizzled.slf4j.Logger

import org.apache.thrift.server.TNonblockingServer
import org.apache.thrift.transport.TNonblockingServerSocket

import java.util.{Map ⇒ JMap}

import java.nio.ByteBuffer

case class ReplicaServer(private val keyValueStore: KeyValueStore, port: Int) {

  private val logger = Logger[this.type]

  private val handler = SnappleServiceHandler(keyValueStore)

  private val processor = new SnappleService.Processor(handler)

  private val server = {
    val serverTransport = new TNonblockingServerSocket(port)
    val server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor))

    server.serve
    logger.info(s"started replica server on port $port")

    server
  }

  private case class SnappleServiceHandler(keyValueStore: KeyValueStore) extends SnappleService.Iface {

    private val logger = Logger[this.type]

    override def ping(): Unit = {
      logger.info("replica server received ping")
    }

    override def propagate(values: JMap[String, TDataType]): Unit = {
      logger.info(s"received propagation")

      val deserialized = ThriftConverter.parsePropagate(values)

      keyValueStore.merge(deserialized)
    }

    override def createEntry(key: String, dataType: String, elementType: Int): Boolean = keyValueStore.entry(key) match {
      case None =>
        val (d, e) = ThriftConverter.parseCreateEntry(dataType, elementType)
        keyValueStore.createEntry(key, d, e)
      case _ => false
    }


    override def removeEntry(key: String): Boolean = keyValueStore.removeEntry(key)

    override def getEntry(key: String): TOptionalDataType = ThriftConverter.convertGetEntry(keyValueStore.entry(key))

    override def modifyEntry(key: String, operation: String, element: ByteBuffer): Boolean = ???

  }
}
