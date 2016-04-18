package snapple.cluster.io

import snapple.finagle.io.{SnappleService, TDataType}

import grizzled.slf4j.Logger

import com.twitter.finagle.{Thrift, ServiceFactory}
import com.twitter.finagle.thrift.ThriftClientRequest

import com.twitter.util.Future

case class ReplicaClient(address: String) {

  private val logger = Logger[this.type]

  private val serviceFactory: ServiceFactory[ThriftClientRequest, Array[Byte]] = Thrift.newClient(address)

  private val client: SnappleService[Future] = new SnappleService.FinagledClient(serviceFactory.toService)

  def disconnect: Unit = {
    logger.info(s"shutdown connection to $address")
    serviceFactory.close
  }

  def ping: Future[Unit] = client.ping

  def propagate(values: Map[String, TDataType]): Future[Unit] = client.propagate(values)

}
