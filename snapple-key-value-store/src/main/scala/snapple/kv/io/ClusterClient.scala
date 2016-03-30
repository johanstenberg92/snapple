package snapple.kv.io

import grizzled.slf4j.Logger

import org.apache.thrift.TException
import org.apache.thrift.transport.TNonblockingSocket
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.async.TAsyncClientManager

case class ClusterClient(hostname: String, port: Int) {

  private val

  private val logger = Logger[this.type]

  private var optionalClient: Option[ClusterService.AsyncClient] = None

  private def client: ClusterService.AsyncClient = optionalClient.getOrElse {
    val protocolFactory = new TBinaryProtocol.Factory
    val clientManager = new TAsyncClientManager
    val transport = new TNonblockingSocket(hostname, port)

    val client = new ClusterService.AsyncClient(protocolFactory, clientManager, transport)

    logger.info(s"connected cluster client to $hostname:$port")

    client
  }
}
