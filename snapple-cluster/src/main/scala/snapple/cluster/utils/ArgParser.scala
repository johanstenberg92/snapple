package snapple.cluster.utils

object ArgParser {

  val DefaultHost = "localhost"

  val DefaultPort = 9000

  val DefaultPropagationInterval = 3

  val DefaultInitialPropagationDelay = 5

  private val Version = "0.0.1"

  private val parser = new scopt.OptionParser[Configuration]("snapple") {
    head("snapple", Version)
    opt[String]('h', "host") action { (x, c) =>
      c.copy(host = x)
    } text(s"the host the snapple instance will run on, default: $DefaultHost.")
    opt[Int]('p', "port") action { (x, c) =>
      c.copy(port = x)
    } text(s"the port the snapple instance will run on, default: $DefaultPort.")
    opt[String]('i', "id") action { (x, c) =>
      c.copy(replicaIdentifier = x)
    } text("the unique identifier for this replica. By default a unique v4 UUID.")
    opt[Seq[String]]('r', "replicas") valueName("host1:port1, host2:port2...") action { (x, c) =>
      c.copy(replicaAddresses = x.map(_.trim.split(":")).map(v => (v(0), v(1).toInt)))
    } text("comma separated list with hosts and ports for initial replicas.")
    opt[Int]('s', "propagation-interval") action { (x, c) =>
      c.copy(propagationInterval = x)
    } text(s"seconds between propagations, default: $DefaultPropagationInterval")
    opt[Int]('d', "initial-propagation-delay") action { (x, c) =>
      c.copy(initialPropagationDelay = x)
    } text(s"seconds before first propagation, default: $DefaultInitialPropagationDelay")
  }

  def apply(args: Array[String]): Configuration = parser.parse(args, Configuration()) match {
    case Some(config) => config
    case None => throw new IllegalArgumentException("config couldn't be parsed")
  }
}
