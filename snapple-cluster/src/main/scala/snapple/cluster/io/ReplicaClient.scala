package snapple.cluster.io

import snapple.thrift.io.{SnappleService, TDataType}

import grizzled.slf4j.Logger

import org.apache.thrift.TException
import org.apache.thrift.transport.TNonblockingSocket
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.async.{TAsyncClientManager, AsyncMethodCallback}

import scala.collection.JavaConverters._

import scala.concurrent.{Future, Promise}

case class ReplicaClient(hostname: String, port: Int) {

  private val logger = Logger[this.type]

  private lazy val client: SnappleService.AsyncClient = {
    val protocolFactory = new TBinaryProtocol.Factory
    val clientManager = new TAsyncClientManager
    val transport = new TNonblockingSocket(hostname, port)

    val client = new SnappleService.AsyncClient(protocolFactory, clientManager, transport)

    logger.info(s"connected replica client to $hostname:$port")

    client
  }

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
  }

}
