package snapple.cluster.io

import snapple.crdts.datatypes.ORSet

import snapple.finagle.serialization.DataSerializer

import snapple.cluster.{KeyValueStore, KeyValueEntry}

import grizzled.slf4j.Logger

import java.util.concurrent.{Executors, TimeUnit}

import scala.concurrent.ExecutionContext.Implicits.global

object ReplicaPropagator {

  val ReplicasKey = "SNAPPLE_INTERNAL_REPLICA_CLIENTS"
}

case class ReplicaPropagator(store: KeyValueStore, propagationInterval: Int) {

  private val logger = Logger[this.type]

  @volatile private var clientConnections: Map[String, ReplicaClient] = Map.empty

  private val propagationExecutor = Executors.newSingleThreadScheduledExecutor

  private val propagationRunnable = new Runnable {

    override def run(): Unit = {
      val serialized = store.entries.map {
        case (k, KeyValueEntry(dataType, elementType)) ⇒ (k → DataSerializer.serialize(dataType, elementType))
      }

      val clients = store.entry(ReplicaPropagator.ReplicasKey) match {
        case Some(KeyValueEntry(set, _)) =>
          val addresses = set.asInstanceOf[ORSet[String]].elements

          clientConnections = clientConnections.filter {
            case (k, v) => addresses.contains(k)
          }

          addresses.filter(a => !clientConnections.contains(a)).foreach {
            case a =>
              val client = ReplicaClient(a)
              clientConnections = clientConnections + (a -> client)
          }

          set.asInstanceOf[ORSet[String]].elements.map(address => ReplicaClient(address))
        case None => Seq.empty
      }

      clients.foreach {
        case client ⇒
          client.propagate(serialized).onFailure {
            case error ⇒
              logger.info(s"connection to ${client.address} failed with exception", error)
          }
      }
    }

  }

  private val scheduledFuture = propagationExecutor
    .scheduleAtFixedRate(propagationRunnable, 0, propagationInterval, TimeUnit.SECONDS)

  def shutdown: Unit = {
    scheduledFuture.cancel(false)
    clientConnections.foreach(_._2.disconnect)
  }

}
