package snapple.cluster.io

import snapple.finagle.io.{SnappleService, TDataType}

import grizzled.slf4j.Logger

import com.twitter.finagle.Thrift

import com.twitter.util.Future

case class ReplicaClient(hostname: String, port: Int) {

  private val logger = Logger[this.type]

  private val client = Thrift.newIface[SnappleService[Future]](s"$hostname:$port")

  def shutdown: Unit = client.close

  def ping: Future[Unit] = client.ping

  /*private val protocolFactory = new TBinaryProtocol.Factory

  private val clientManager = new TAsyncClientManager

  private val socket = new TNonblockingSocket(hostname, port)

  logger.info(s"connected replica client to $hostname:$port")

  private def client: SnappleService.AsyncClient = new SnappleService.AsyncClient(protocolFactory, clientManager, socket)

  def shutdown: Unit = socket.close

  def ping: Future[Unit] = {
    val promise = Promise[Unit]()
    client.ping(PingCallback(promise))
    promise.future
  }

  private case class PingCallback(promise: Promise[Unit]) extends AsyncMethodCallback[SnappleService.AsyncClient.ping_call] {

    override def onComplete(result: SnappleService.AsyncClient.ping_call): Unit = {
      logger.info(s"successfully pinged $hostname:$port")
      promise.success(Unit)
    }

    override def onError(error: Exception): Unit = {
      logger.error(s"error pinging $hostname:$port", error)
      promise.failure(error)
    }

  }

  def propagate(values: Map[String, TDataType]): Future[Unit] = {
    val promise = Promise[Unit]()
    client.propagate(values.asJava, PropagateCallback(promise))
    promise.future
  }

  private case class PropagateCallback(promise: Promise[Unit]) extends AsyncMethodCallback[SnappleService.AsyncClient.propagate_call] {

    override def onComplete(result: SnappleService.AsyncClient.propagate_call): Unit = {
      logger.info(s"successfully propagated to $hostname:$port")
      promise.success(Unit)
    }

    override def onError(error: Exception): Unit = {
      logger.error(s"error propagating to $hostname:$port", error)
      promise.failure(error)
    }
  }*/

}
