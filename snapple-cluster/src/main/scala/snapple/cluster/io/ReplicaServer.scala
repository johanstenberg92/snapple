package snapple.cluster.io

import snapple.thrift.io.{TDataType, TOptionalString, TOptionalDataType, TOptionalElementType, SnappleService}

import snapple.thrift.serialization.DataSerializer

import snapple.cluster.KeyValueStore

import grizzled.slf4j.Logger

import org.apache.thrift.server.TNonblockingServer
import org.apache.thrift.transport.TNonblockingServerSocket

import java.util.{Map ⇒ JMap}

import scala.collection.JavaConverters._

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
      val deserialized = values.asScala.toMap.map {
        case (k, v) ⇒ (k → DataSerializer.deserialize(v))
      }
      keyValueStore.merge(deserialized)
    }

    override def createEntry(key: String, dataType: String, elementType: TOptionalString): Boolean = ???

    override def removeEntry(key: String): Boolean = ???

    override def getEntry(key: String): TOptionalDataType = ???

    override def modifyEntry(key: String, operation: String, element: TOptionalElementType): Boolean = ???

  }
}
