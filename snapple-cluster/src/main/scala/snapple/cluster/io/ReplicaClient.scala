package snapple.cluster.io

import snapple.thrift.io.{ReplicaService, SnappleService}

import grizzled.slf4j.Logger

import org.apache.thrift.TException
import org.apache.thrift.transport.TNonblockingSocket
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.async.{TAsyncClientManager, AsyncMethodCallback}

case class ReplicaClient(hostname: String, port: Int) {

  private val logger = Logger[this.type]

  private var optionalClient: Option[ReplicaService.AsyncClient] = None

  private def client: ReplicaService.AsyncClient = optionalClient.getOrElse {
    val protocolFactory = new TBinaryProtocol.Factory
    val clientManager = new TAsyncClientManager
    val transport = new TNonblockingSocket(hostname, port)

    val client = new ReplicaService.AsyncClient(protocolFactory, clientManager, transport)

    logger.info(s"connected replica client to $hostname:$port")

    client
  }

  def ping(lambda: Boolean => Unit): Unit = client.ping(PingMethodCallback(lambda))

  case class PingMethodCallback(lambda: Boolean => Unit) extends AsyncMethodCallback[SnappleService.AsyncClient.ping_call] {

    override def onComplete(result: SnappleService.AsyncClient.ping_call): Unit = {
      logger.info(s"successfully pinged $hostname:$port")
      lambda(true)
    }

    override def onError(error: Exception): Unit = {
      logger.error(s"error pinging $hostname:$port", error)
      lambda(false)
    }
  }

}