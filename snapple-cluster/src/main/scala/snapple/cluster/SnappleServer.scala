package snapple.cluster

import snapple.crdts.datatypes.ORSet

import snapple.finagle.io.StringElementKind

import snapple.cluster.io.{ReplicaServer, ReplicaClient, ReplicaPropagator}

import snapple.cluster.utils.Configuration

object SnappleServer {

  def run(config: Configuration = Configuration()): SnappleServer = SnappleServer(config)
}

case class SnappleServer(config: Configuration) {

  private val store = KeyValueStore()

  private val server = ReplicaServer(store, config.port, config.replicaIdentifier)

  private val propagator = {
    val host = config.replicaIdentifier

    var orset = ORSet()

    config.replicaAddresses.foreach {
      case (hostname, port) => orset = orset + (host, s"$hostname:$port")
    }

    orset = orset + (host, s"${config.host}:${config.port}")

    store.createEntry(ReplicaPropagator.ReplicasKey, KeyValueEntry(orset, StringElementKind))

    ReplicaPropagator(store, config.propagationInterval)
  }

  def shutdown: Map[String, KeyValueEntry] = {
    propagator.shutdown
    server.shutdown
    store.entries
  }
}
