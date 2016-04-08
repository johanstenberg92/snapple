package snapple.cluster.io

import snapple.thrift.serialization.DataSerializer

import snapple.cluster.KeyValueStore

import grizzled.slf4j.Logger

import java.util.concurrent.{Executors, TimeUnit}

import scala.concurrent.ExecutionContext.Implicits.global

object ReplicaPropagator {

  private val SecondsBetweenPropagations = 3

}

case class ReplicaPropagator(private val store: KeyValueStore, private val initialClients: Seq[ReplicaClient]) {

  import ReplicaPropagator._

  private val logger = Logger[this.type]

  @volatile private var clients: Set[ReplicaClient] = initialClients.toSet

  def addClient(client: ReplicaClient): Unit = {
    clients = clients + client
  }

  def removeClient(clientHostname: String, clientPort: Int): Unit = {
    clients = clients.filterNot {
      case ReplicaClient(hostname, port) => hostname == clientHostname && port == clientPort
    }
  }

  private val propagationExecutor = Executors.newSingleThreadScheduledExecutor

  private val propagationRunnable = new Runnable {

    override def run(): Unit = {
      val serialized = store.entries.map {
        case (k, v) => (k -> DataSerializer.serialize(v))
      }

      clients.foreach {
        case client =>
          client.propagate(serialized).onFailure {
            case error =>
              logger.info(s"connection to ${client.hostname}:${client.port} failed with exception", error)
              clients = clients - client
          }
      }
    }

  }

  propagationExecutor.scheduleAtFixedRate(propagationRunnable, 0, SecondsBetweenPropagations, TimeUnit.SECONDS)

}
