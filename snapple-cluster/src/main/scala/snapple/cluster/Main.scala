package snapple.cluster

import snapple.cluster.utils.Configuration
import snapple.cluster.io.{ReplicaServer, ReplicaClient}

object Main {

  def main(args: Array[String]): Unit = {
    val server = ReplicaServer(Configuration.Port)

    val clients = Configuration.ReplicaAddresses.map {
      case (hostname, port) ⇒ ReplicaClient(hostname, port)
    }

    val store = KeyValueStore()
  }
}
