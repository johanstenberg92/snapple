package snapple.cluster

import snapple.cluster.io.{ReplicaServer, ReplicaClient, ReplicaPropagator}

import snapple.cluster.utils.Configuration

object SnappleServer {

  def run(config: Configuration = Configuration()): SnappleServer = SnappleServer(config)
}

case class SnappleServer(config: Configuration) {

  private val store = KeyValueStore()

  private val server = ReplicaServer(store, config.port, config.replicaIdentifier)

  private val propagator = {
    val initialClients = config.replicaAddresses.map {
      case (hostname, port) ⇒ ReplicaClient(hostname, port)
    }

    ReplicaPropagator(store, initialClients, config.propagationInterval)
  }

  def shutdown: Map[String, KeyValueEntry] = {
    propagator.shutdown
    server.shutdown
    store.entries
  }
}
