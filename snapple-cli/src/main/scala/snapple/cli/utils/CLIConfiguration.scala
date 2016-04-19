package snapple.cli.utils

case class CLIConfiguration(
  host: String = CLIArgParser.DefaultHost,
  port: Int = CLIArgParser.DefaultPort,
  command: String = CLIArgParser.DefaultCommand,
  key: Option[String] = None,
  crdt: Option[String] = None,
  op: Option[String] = None,
  elementKind: Option[String] = None,
  element: Option[String] = None
)
