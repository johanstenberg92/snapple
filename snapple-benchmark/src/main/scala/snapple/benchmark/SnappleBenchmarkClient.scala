package snapple.benchmark

import snapple.client.SnappleEntry

import snapple.crdts.datatypes.{DataType, ORSet}

import snapple.finagle.utils.FinagleUtils._
import snapple.finagle.io._
import snapple.finagle.serialization.DataSerializer

import java.nio.ByteBuffer

import com.twitter.finagle.{Thrift, ServiceFactory}
import com.twitter.finagle.thrift.ThriftClientRequest
import com.twitter.finagle.loadbalancer.{LoadBalancerFactory, SnappleLoadBalancer}
import com.twitter.finagle.stats.InMemoryStatsReceiver
import com.twitter.finagle.service.StatsFilter
import com.twitter.finagle.Service

import com.twitter.util.{Future => TwitterFuture}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class SnappleBenchmarkClient(host: String, port: Int, statsReceiver: InMemoryStatsReceiver, parallel: Boolean) {

  private val statsFilter = new StatsFilter[ThriftClientRequest, Array[Byte]](statsReceiver)

  private val balancer = SnappleLoadBalancer()

  private val serviceFactory: ServiceFactory[ThriftClientRequest, Array[Byte]] =
    Thrift
      .client
      .withSessionPool
      .maxSize(1)
      .withLoadBalancer(balancer)
      .newClient(s"$host:$port")

  private val service: Service[ThriftClientRequest, Array[Byte]] = serviceFactory.toService

  private val client: SnappleService[TwitterFuture] =
    if (parallel) new SnappleService.FinagledClient(service)
    else new SnappleService.FinagledClient(statsFilter andThen service)

  def disconnect: Unit = {
    serviceFactory.close
  }

  def modifyEntry(key: String, operation: OpKind, value: Option[Any] = None): Future[Boolean] = {
    val bb = value.map(DataSerializer.serializeElement).getOrElse(ByteBuffer.allocate(0))

    toScalaFuture(client.modifyEntry(key, operation.id, bb))
  }

}
