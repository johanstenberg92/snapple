package snapple.cluster.io

import snapple.thrift.io._

import snapple.thrift.serialization.DataSerializer

import snapple.cluster.{SnappleServer, KeyValueStore}

import grizzled.slf4j.Logger

import org.apache.thrift.server.TNonblockingServer
import org.apache.thrift.transport.TNonblockingServerSocket

import java.util.{Map ⇒ JMap}
import java.util.concurrent.{ExecutorService, Executors}

import java.nio.ByteBuffer

case class ReplicaServer(keyValueStore: KeyValueStore, port: Int, replicaIdentifier: String) {

  private val logger = Logger[this.type]

  private val handler = SnappleServiceHandler(keyValueStore)

  private val processor = new SnappleService.Processor(handler)

  private val thriftOpHandler: ThriftOpHandler = ThriftOpHandler(replicaIdentifier)

  private val (server, executor): (TNonblockingServer, ExecutorService) = {
    val serverTransport = new TNonblockingServerSocket(port)
    val server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor))

    val runnable = new Runnable { override def run(): Unit = server.serve }
    val executor = Executors.newSingleThreadExecutor
    executor.submit(runnable)

    logger.info(s"started replica server on port $port")

    (server, executor)
  }

  def shutdown: Unit = {
    logger.info("shutting down replica server")
    server.stop
    executor.shutdown
  }

  private case class SnappleServiceHandler(keyValueStore: KeyValueStore) extends SnappleService.Iface {

    private val logger = Logger[this.type]

    override def ping(): Unit = {
      logger.info("replica server received ping")
    }

    override def propagate(values: JMap[String, TDataType]): Unit = {
      logger.info(s"received propagation")

      val deserialized = ThriftMethodHelper.parsePropagate(values)

      keyValueStore.merge(deserialized)
    }

    override def createEntry(key: String, dataType: String, elementType: Int): Boolean = keyValueStore.entry(key) match {
      case None =>
        val keyValueEntry = ThriftMethodHelper.parseCreateEntry(dataType, elementType)
        keyValueStore.createEntry(key, keyValueEntry)
      case _ => false
    }


    override def removeEntry(key: String): Boolean = keyValueStore.removeEntry(key)

    override def getEntry(key: String): TOptionalDataType = ThriftMethodHelper.convertGetEntry(keyValueStore.entry(key))

    override def modifyEntry(key: String, operation: String, element: ByteBuffer): Boolean = keyValueStore.entry(key) match {
      case Some(kve) =>
        val op = thriftOpHandler.handleOp(kve.thriftDataType, kve.elementType, ThriftOpType(operation), element)
        kve.modify(op)
        true
      case None => false
    }

  }
}
