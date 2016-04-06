package snapple.cluster.io

import snapple.thrift.io.{TDataType, ReplicaService}

import grizzled.slf4j.Logger

import java.util.{Map => JMap}

import org.apache.thrift.server.TNonblockingServer
import org.apache.thrift.transport.TNonblockingServerSocket

case class ReplicaServer(port: Int) {

  private val logger = Logger[this.type]

  private val handler = new ReplicaServiceHandler

  private val processor = new ReplicaService.Processor(handler)

  private val server = {
    val serverTransport = new TNonblockingServerSocket(port)
    val server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor))

    server.serve
    logger.info(s"started replica server on port $port")

    server
  }
}

case class ReplicaServiceHandler() extends ReplicaService.Iface {

  private val logger = Logger[this.type]

  override def ping(): Unit = {
    logger.info("replica server received ping")
  }

  override def propagate(orsets: JMap[String, TDataType]): Boolean = {
    logger.info(s"received propagation")
    true
  }

}
