package snapple.client.io

import snapple.client.SnappleEntry

import snapple.crdts.datatypes.DataType

import snapple.thrift.io._
import snapple.thrift.serialization.DataSerializer

import grizzled.slf4j.Logger

import org.apache.thrift.TException
import org.apache.thrift.transport.TNonblockingSocket
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.async.{TAsyncClientManager, AsyncMethodCallback}

import scala.concurrent.{Future, Promise}

import java.nio.ByteBuffer

object Client {

  val DefaultPort = 9000

}

case class Client(hostname: String, port: Int = Client.DefaultPort) {

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

  def createEntry(key: String, dataType: ThriftDataType, elementType: ThriftElementType): Future[Boolean] = {
    val promise = Promise[Boolean]()
    client.createEntry(key, dataType.id, elementType.id, CreateEntryCallback(promise))
    promise.future
  }

  private case class CreateEntryCallback(promise: Promise[Boolean]) extends AsyncMethodCallback[SnappleService.AsyncClient.createEntry_call] {

    override def onComplete(result: SnappleService.AsyncClient.createEntry_call): Unit = {
      promise.success(result.getResult)
    }

    override def onError(error: Exception): Unit = {
      promise.failure(error)
    }

  }

  def removeEntry(key: String): Future[Boolean] = {
    val promise = Promise[Boolean]()
    client.removeEntry(key, RemoveEntryCallback(promise))
    promise.future
  }

  private case class RemoveEntryCallback(promise: Promise[Boolean]) extends AsyncMethodCallback[SnappleService.AsyncClient.removeEntry_call] {

    override def onComplete(result: SnappleService.AsyncClient.removeEntry_call): Unit = {
      promise.success(result.getResult)
    }

    override def onError(error: Exception): Unit = {
      promise.failure(error)
    }

  }

  def getEntry(key: String): Future[Option[SnappleEntry]] = {
    val promise = Promise[Option[SnappleEntry]]()
    client.getEntry(key, GetEntryCallback(promise))
    promise.future
  }

  private case class GetEntryCallback(promise: Promise[Option[SnappleEntry]]) extends AsyncMethodCallback[SnappleService.AsyncClient.getEntry_call] {

    override def onComplete(result: SnappleService.AsyncClient.getEntry_call): Unit = {
      val optionalDataType = result.getResult

      if (optionalDataType.isSetDataType) {
        val tDataType = optionalDataType.getDataType
        val (dataType, elementType) = DataSerializer.deserialize(optionalDataType.getDataType)
        promise.success(Some(SnappleEntry(dataType, elementType)))
      } else promise.success(None)
    }

    override def onError(error: Exception): Unit = {
      promise.failure(error)
    }

  }

  def modifyEntry(key: String, operation: ThriftOpType, value: Option[Any] = None): Future[Boolean] = {
    val promise = Promise[Boolean]()

    val bb = value.map(DataSerializer.serializeElementType).getOrElse(ByteBuffer.allocate(0))
    client.modifyEntry(key, operation.id, bb, ModifyEntryCallback(promise))
    promise.future
  }

  private case class ModifyEntryCallback(promise: Promise[Boolean]) extends AsyncMethodCallback[SnappleService.AsyncClient.modifyEntry_call] {

    override def onComplete(result: SnappleService.AsyncClient.modifyEntry_call): Unit = {
      promise.success(result.getResult)
    }

    override def onError(error: Exception): Unit = {
      promise.failure(error)
    }

  }

}
