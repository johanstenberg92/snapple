package snapple.cli

import snapple.cli.utils.{CLIArgParser, IOFutureHelper}

import snapple.client.io.SnappleClient

import snapple.finagle.io.{AddOpKind, RemoveOpKind, ClearOpKind}

import scala.util.{Success, Failure}

object Main {

  import CLIArgParser._

  def main(args: Array[String]): Unit = {
    val config = CLIArgParser(args)

    val client = SnappleClient.singleHost(config.host, config.port)
    val futureHelper = IOFutureHelper(config.host, config.port)

    val host = s"${config.host}:${config.port}"

    config.command match {
      case Ping =>
        futureHelper.get(client.ping)
        println(s"successfully pinged $host")
      case Read =>
        val key = config.key.get
        futureHelper.get(client.entry(key)) match {
          case Some(entry) =>
            println(s"found entry with type ${entry.dataKind}")
            println(s"entry: $entry")
          case None => println(s"no entry found at key $key")
        }
      case Delete =>
        val key = config.key.get
        val success = futureHelper.get(client.removeEntry(key))

        if (success) println(s"successfully deleted entry $key")
        else println(s"couldn't find entry $key")
      case Modify =>
        val key = config.key.get
        val op = config.op.get match {
          case "add" => AddOpKind
          case "remove" => RemoveOpKind
          case "clear" => ClearOpKind
        }
        val v: Option[Any] = config.elementKind match {
          case Some("boolean") => Some(config.element.get.toBoolean)
          case Some("byte") => Some(config.element.get.toByte)
          case Some("int") => Some(config.element.get.toInt)
          case Some("long") => Some(config.element.get.toLong)
          case Some("double") => Some(config.element.get.toDouble)
          case Some("string") => Some(config.element.get)
          case _ => None
        }

        val success = futureHelper.get(client.modifyEntry(key, op, v))

        if (success) println(s"successfully modified $key")
        else println(s"couldn't find entry $key")
      case Hosts =>
        val hosts = futureHelper.get(client.getHosts)
        println(s"current hosts are: $hosts")
      case Dump =>
        val entries = futureHelper.get(client.entries)
        println(s"found ${entries.size} entries.")
        println("***")
        println(s"$entries")
    }
  }

}
