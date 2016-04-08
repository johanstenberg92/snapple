package snapple.cluster

import snapple.cluster.utils.Configuration

import snapple.cluster.io.{ReplicaServer, ReplicaClient, ReplicaPropagator}

object Main {

  def main(args: Array[String]): Unit = {
    val store = KeyValueStore()

    val server = ReplicaServer(store, Configuration.Port)

    val initialClients = Configuration.ReplicaAddresses.map {
      case (hostname, port) ⇒ ReplicaClient(hostname, port)
    }

    val propagator = ReplicaPropagator(store, initialClients)
  }
}
