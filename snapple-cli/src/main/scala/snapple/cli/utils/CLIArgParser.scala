package snapple.cli.utils

object CLIArgParser {

  val DefaultHost = "localhost"

  val DefaultPort = 9000

  val Ping = "ping"

  val Read = "read"

  val Delete = "delete"

  val Modify = "modify"

  val Hosts = "hosts"

  val Dump = "dump"

  val DefaultCommand = Ping

  private val Version = "0.0.1"

  private val parser = new scopt.OptionParser[CLIConfiguration]("snapple-cli") {
    head("snapple-cli", Version)
    opt[String]('h', "host") action { (x, c) =>
      c.copy(host = x)
    } text(s"which host to connect to, default: $DefaultHost.")
    opt[Int]('p', "port") action { (x, c) =>
      c.copy(port = x)
    } text(s"which port to connect to, default: $DefaultPort.")
    note("\n")
    help("help") text("prints this usage text")
    note("\n")
    cmd(Ping) action { (_, c) =>
      c.copy(command = Ping)
    } text ("pings the snapple instance, this is the default command which is executed if no other command is provided.")
    note("\n")
    cmd(Read) action { (_, c) =>
      c.copy(command = Read)
    } text ("reads an entry from the snapple instance.") children(
      opt[String]("key") required() action { (x, c) =>
        c.copy(key = Some(x))
      } text ("key to entry to be read.")
    )
    note("\n")
    cmd(Delete) action { (_, c) =>
      c.copy(command = Delete)
    } text ("delete an entry from the snapple instance.") children(
      opt[String]("key") required() action { (x, c) =>
        c.copy(key = Some(x))
      } text ("key to entry to be deleted.")
    )
    note("\n")
    cmd(Modify) action { (_, c) =>
      c.copy(command = Delete)
    } text ("modify an entry in the snapple instance.") children(
      opt[String]("key") required() action { (x, c) =>
        c.copy(key = Some(x))
      } text ("key to entry to be modified."),
      opt[String]("operation") required() action { (x, c) =>
        c.copy(op = Some(x))
      } validate { x =>
        if (validOps.contains(x)) success
        else failure(s"$x is an invalid operation.")
      } text ("operation to be carried out. Can be any of *add*, *remove* and *clear*."),
      opt[String]("type") action { (x, c) =>
        c.copy(elementKind = Some(x))
      } validate { x =>
        if (validEntryTypes.contains(x)) success
        else failure(s"$x is an invalid entry type.")
      } text ("the type of the entry element you are adding or removing. Accepts only *boolean*, *byte*, *int*, *long*, *double* and *string*."),
      opt[String]("element") action { (x, c) =>
        c.copy(element = Some(x))
      } text ("the entry element you are adding or removing."),
      checkConfig { c =>
        if (c.command == Modify && (!c.element.isEmpty || !c.elementKind.isEmpty)) {
          if ((c.elementKind.isEmpty && !c.element.isEmpty) || (!c.elementKind.isEmpty && c.element.isEmpty))
            failure(s"if element type is provided then provide an element or vice-versa.")
          else if (validateType(c.element.get, c.elementKind.get))
            success
          else
            failure(s"${c.element.get} is not of type ${c.elementKind.get}")
        } else
          success
      }
    )
    note("\n")
    cmd(Hosts) action { (_, c) =>
      c.copy(command = Hosts)
    } text ("reads the current replicas")
    note("\n")
    cmd(Dump) action { (_, c) =>
      c.copy(command = Dump)
    } text ("dumps all entries to std-io.")
  }

  private val validOps = Seq("add", "remove", "clear")

  private val validEntryTypes = Seq("boolean", "byte", "int", "long", "double", "string")

  private def validateType(t: String, e: String): Boolean = try {
    e match {
      case "boolean" => t.toBoolean
      case "byte" => t.toByte
      case "int" => t.toInt
      case "long" => t.toLong
      case "double" => t.toDouble
      case "string" =>
    }

    true
  } catch {
    case _: Throwable => false
  }

  def apply(args: Array[String]): CLIConfiguration = parser.parse(args, CLIConfiguration()) match {
    case Some(config) => config
    case None => sys.exit(-1)
  }
}
