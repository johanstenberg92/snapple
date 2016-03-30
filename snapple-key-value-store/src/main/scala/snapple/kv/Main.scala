package snapple.kv

import snapple.kv.utils.Configuration
import snapple.kv.io.{ClusterClient, ClusterServer}

object Main {

  def main(args: Array[String]): Unit = {
    val server = ClusterServer(Configuration.Port)
    val clients = Configuration.ClusterHosts.map {
      case (hostname, port) => ClusterClient(hostname, port)
    }


  }
}
