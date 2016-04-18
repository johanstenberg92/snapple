package snapple.cluster.utils

import java.util.UUID

case class Configuration(
  host: String = ArgParser.DefaultHost,
  port: Int = ArgParser.DefaultPort,
  replicaIdentifier: String = UUID.randomUUID.toString,
  replicaAddresses: Seq[(String, Int)] = Seq.empty,
  propagationInterval: Int = ArgParser.DefaultPropagationInterval
)
