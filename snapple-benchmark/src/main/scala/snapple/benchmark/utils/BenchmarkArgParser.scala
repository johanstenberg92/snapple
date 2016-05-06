package snapple.benchmark.utils

object BenchmarkArgParser {

  val DefaultHost = "localhost"

  val DefaultPort = 9000

  val DefaultClients = 50

  val DefaultRequests = 100000

  val DefaultByteSize = 10

  val DefaultKeys = 1

  val SequentialClients = 1

  val SequentialRequests = 10000

  private val Version = "0.0.1"

  private val parser = new scopt.OptionParser[BenchmarkConfiguration]("snapple-benchmark") {
    head("snapple-benchmark", Version)
    opt[String]('h', "host") action { (x, c) =>
      c.copy(host = x)
    } text(s"the host the snapple instance will run on, default: $DefaultHost.")
    opt[Int]('p', "port") action { (x, c) =>
      c.copy(port = x)
    } text(s"the port the snapple instance will run on, default: $DefaultPort.")
    opt[Int]('c', "clients") action { (x, c) =>
      c.copy(clients = x)
    } text(s"number of clients, default: $DefaultClients. Note here each client only has 1 thread in pool usually finagle default.")
    opt[Int]('r', "requests") action { (x, c) =>
      c.copy(requests = x)
    } text(s"number of requests. Default: $DefaultRequests.")
    opt[Int]('d', "bytes") action { (x, c) =>
      c.copy(byteSize = x)
    } text(s"number of bytes in requests, default: $DefaultByteSize.")
    opt[Int]('k', "keys") action { (x, c) =>
      c.copy(keys = x)
    } text(s"number of keys used, each entry is an OR-Set, default: $DefaultKeys.")
    opt[Unit]("sequential") action { (_, c) =>
      c.copy(sequential = true)
    } text(s"if used, sequential and not parallel execution. If used, clients are $SequentialClients and requests are $SequentialRequests.")
    note("\n")
    help("help") text("prints this usage text")
  }

  def apply(args: Array[String]): BenchmarkConfiguration = parser.parse(args, BenchmarkConfiguration()) match {
    case Some(config) => config
    case None => sys.exit(-1)
  }
}
