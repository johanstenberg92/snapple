package snapple.cluster

import snapple.crdts.datatypes.ORSet

import snapple.finagle.io.StringElementKind

import snapple.cluster.io.{ReplicaServer, ReplicaClient, ReplicaPropagator}

import snapple.cluster.utils.Configuration

object SnappleServer {

  def run(config: Configuration = Configuration()): SnappleServer = SnappleServer(config)
}

case class SnappleServer(config: Configuration) {

  val store = KeyValueStore()

  val server = ReplicaServer(store, config.port, config.replicaIdentifier)

  val propagator = {
    val id = config.replicaIdentifier

    var orset = ORSet()

    config.replicaAddresses.foreach {
      case (hostname, port) => orset = orset + (id, s"$hostname:$port")
    }

    orset = orset + (id, s"${config.host}:${config.port}")

    store.createEntry(ReplicaPropagator.ReplicasKey, KeyValueEntry(orset, StringElementKind))

    ReplicaPropagator(store, config)
  }

  def shutdown: Map[String, KeyValueEntry] = {
    propagator.shutdown
    server.shutdown
    store.entries
  }
}
