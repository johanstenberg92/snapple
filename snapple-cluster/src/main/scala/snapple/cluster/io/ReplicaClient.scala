package snapple.cluster.io

import snapple.finagle.io.{SnappleService, TDataType}

import grizzled.slf4j.Logger

import com.twitter.finagle.{Thrift, ServiceFactory}
import com.twitter.finagle.thrift.ThriftClientRequest

import com.twitter.util.Future

case class ReplicaClient(hostname: String, port: Int) {

  private val logger = Logger[this.type]

  private val serviceFactory: ServiceFactory[ThriftClientRequest, Array[Byte]] = Thrift.newClient(s"$hostname:$port")

  private val client: SnappleService[Future] = new SnappleService.FinagledClient(serviceFactory.toService)

  def disconnect: Unit = {
    logger.info(s"shutdown connection to $hostname:$port")
    serviceFactory.close
  }

  def ping: Future[Unit] = client.ping

  def propagate(values: Map[String, TDataType]): Future[Unit] = client.propagate(values)

}
