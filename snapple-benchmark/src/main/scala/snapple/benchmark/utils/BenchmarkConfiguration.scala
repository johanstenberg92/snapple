package snapple.benchmark.utils

case class BenchmarkConfiguration(
  host: String = BenchmarkArgParser.DefaultHost,
  port: Int = BenchmarkArgParser.DefaultPort,
  clients: Int = BenchmarkArgParser.DefaultClients,
  requests: Int = BenchmarkArgParser.DefaultRequests,
  byteSize: Int = BenchmarkArgParser.DefaultByteSize,
  keys: Int = BenchmarkArgParser.DefaultKeys,
  sequential: Boolean = false
)
