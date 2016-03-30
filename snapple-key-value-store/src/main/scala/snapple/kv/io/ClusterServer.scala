package snapple.kv.io

import grizzled.slf4j.Logger

import java.util.{Map => JMap}

import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TNonblockingServerSocket;

case class ClusterServer(port: Int) {

  private val logger = Logger[this.type]

  private val handler = new ClusterServiceHandler

  private val processor = new ClusterService.Processor(handler)

  private val server = {
    val serverTransport = new TNonblockingServerSocket(port)
    val server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor))

    server.serve
    logger.info(s"started cluster service on port $port")

    server
  }
}

case class ClusterServiceHandler() extends ClusterService.Iface {

  private val logger = Logger[this.type]

  override def ping() {
    logger.info("received ping!")
  }

  override def propagate(orsets: JMap[String, TORSet]): Boolean = {
    logger.info(s"received $orsets")
    true
  }

}
