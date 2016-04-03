package snapple.kv

import snapple.kv.utils.Configuration
import snapple.kv.io.{ReplicaServer, ReplicaClient}

object Main {

  def main(args: Array[String]): Unit = {
    val server = ReplicaServer(Configuration.Port)

    val clients = Configuration.ReplicaAddresses.map {
      case (hostname, port) ⇒ ReplicaClient(hostname, port)
    }

    val store = KeyValueStore()
  }
}
