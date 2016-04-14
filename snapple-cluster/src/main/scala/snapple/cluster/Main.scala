package snapple.cluster

import snapple.cluster.utils.ArgParser

import snapple.cluster.io.{ReplicaServer, ReplicaClient, ReplicaPropagator}

object Main {

  def main(args: Array[String]): Unit = {
    val config = ArgParser(args)

    val store = KeyValueStore()

    val server = ReplicaServer(store, config.port, config.replicaIdentifier)

    val initialClients = config.replicaAddresses.map {
      case (hostname, port) ⇒ ReplicaClient(hostname, port)
    }

    val propagator = ReplicaPropagator(store, initialClients)
  }
}
