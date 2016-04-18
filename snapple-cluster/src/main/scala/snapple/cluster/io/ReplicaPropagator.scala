package snapple.cluster.io

import snapple.crdts.datatypes.ORSet

import snapple.finagle.serialization.DataSerializer

import snapple.finagle.utils.FinagleUtils._

import snapple.cluster.{KeyValueStore, KeyValueEntry}

import snapple.cluster.utils.Configuration

import grizzled.slf4j.Logger

import java.util.concurrent.{Executors, TimeUnit}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ReplicaPropagator {

  val ReplicasKey = "SNAPPLE_INTERNAL_REPLICA_CLIENTS"
}

case class ReplicaPropagator(store: KeyValueStore, config: Configuration) {

  private val logger = Logger[this.type]

  @volatile private var clients: Map[String, ReplicaClient] = Map.empty

  private val ownAddress = s"${config.host}:${config.port}"

  private val propagationExecutor = Executors.newSingleThreadScheduledExecutor

  private val propagationRunnable = new Runnable {

    override def run(): Unit = propagate

  }

  def propagate: Future[Unit] = {
    val serialized = store.entries.map {
      case (k, KeyValueEntry(dataType, elementKind)) ⇒ (k → DataSerializer.serialize(dataType, elementKind))
    }

    store.entry(ReplicaPropagator.ReplicasKey) match {
      case Some(KeyValueEntry(set, _)) ⇒
        val addresses = set.asInstanceOf[ORSet[String]].elements.filter(_ != ownAddress)

        clients = clients.filter {
          case (k, v) ⇒ addresses.contains(k)
        }

        addresses.filter(a ⇒ !clients.contains(a)).foreach {
          case a ⇒
            val client = ReplicaClient(a)
            clients = clients + (a → client)
        }
      case None ⇒ Seq.empty
    }

    val futures: Seq[Future[Unit]] = clients.values.map {
      case client ⇒
        val f = client.propagate(serialized)
        f.onFailure {
          case error ⇒ logger.info(s"connection to ${client.address} failed with exception", error)
        }

        f.map(_ => ())
    }.toSeq

    Future.sequence(futures).map(_ => ())
  }

  private val scheduledFuture = propagationExecutor
    .scheduleAtFixedRate(propagationRunnable, config.initialPropagationDelay, config.propagationInterval, TimeUnit.SECONDS)

  def shutdown: Unit = {
    scheduledFuture.cancel(false)
    clients.values.foreach(_.disconnect)
  }

}
