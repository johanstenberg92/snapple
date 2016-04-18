package snapple.client.io

import snapple.client.SnappleEntry

import snapple.crdts.datatypes.DataType

import snapple.finagle.FinagleUtils._
import snapple.finagle.io._
import snapple.finagle.serialization.DataSerializer

import grizzled.slf4j.Logger

import java.nio.ByteBuffer

import com.twitter.finagle.{Thrift, ServiceFactory}
import com.twitter.finagle.thrift.ThriftClientRequest

import com.twitter.util.{Future => TwitterFuture}

import scala.concurrent.Future

object SnappleClient {

  val DefaultPort = 9000

  def singleHost(hostname: String, port: Int = DefaultPort): SnappleClient = SnappleClient(Seq((hostname, port)))

}

case class SnappleClient(hosts: Seq[(String, Int)]) {

  private val logger = Logger[this.type]

  private val hostsString = hosts.foldLeft("") { case (acc, (h, p)) => s"$acc,$h:$p" }.drop(1)

  private val serviceFactory: ServiceFactory[ThriftClientRequest, Array[Byte]] = Thrift.newClient(hostsString)

  private val client: SnappleService[TwitterFuture] = new SnappleService.FinagledClient(serviceFactory.toService)

  logger.info("connected snapple client")

  def disconnect: Unit = {
    logger.info("shutdown connection")
    serviceFactory.close
  }

  def ping: Future[Unit] = toScalaFuture(client.ping)

  def createEntry(key: String, dataKind: DataKind, elementKind: ElementKind): Future[Boolean] =
    toScalaFuture(client.createEntry(key, dataKind.id, elementKind.id))

  def removeEntry(key: String): Future[Boolean] = toScalaFuture(client.removeEntry(key))

  def entry(key: String): Future[Option[SnappleEntry]] = {
    val twitterFuture = client.getEntry(key).map {
      case TOptionalDataType(Some(v)) =>
        val (dataType, elementKind) = DataSerializer.deserialize(v)
        Some(SnappleEntry(dataType, elementKind))
      case _ => None
    }

    toScalaFuture(twitterFuture)
  }

  def modifyEntry(key: String, operation: OpKind, value: Option[Any] = None): Future[Boolean] = {
    val bb = value.map(DataSerializer.serializeElement).getOrElse(ByteBuffer.allocate(0))

    toScalaFuture(client.modifyEntry(key, operation.id, bb))
  }

}
