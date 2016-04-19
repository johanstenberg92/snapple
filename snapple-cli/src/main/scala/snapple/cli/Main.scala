package snapple.cli

import snapple.cli.utils.{CLIArgParser, IOFutureHelper}

import snapple.client.io.SnappleClient

import snapple.finagle.io._

import scala.util.{Success, Failure}

object Main {

  import CLIArgParser._

  def main(args: Array[String]): Unit = {
    val config = CLIArgParser(args)

    val client = SnappleClient.singleHost(config.host, config.port)
    val futureHelper = IOFutureHelper(config.host, config.port)

    val host = s"${config.host}:${config.port}"

    config.command match {
      case Ping ⇒
        futureHelper.get(client.ping)
        println(s"successfully pinged $host")
      case Create ⇒
        val key = config.key.get

        val crdt = config.crdt.get match {
          case "orset"         ⇒ ORSetDataKind
          case "versionvector" ⇒ VersionVectorDataKind
        }

        val elementKind = config.elementKind match {
          case Some("boolean") ⇒ BooleanElementKind
          case Some("byte")    ⇒ ByteElementKind
          case Some("int")     ⇒ IntElementKind
          case Some("long")    ⇒ LongElementKind
          case Some("double")  ⇒ DoubleElementKind
          case Some("string")  ⇒ StringElementKind
          case _               ⇒ NoElementKind
        }

        val success = futureHelper.get(client.createEntry(key, crdt, elementKind))
        if (success) println(s"successfully created $key")
        else println(s"couldn't create $key")
      case Read ⇒
        val key = config.key.get

        futureHelper.get(client.entry(key)) match {
          case Some(entry) ⇒
            println(s"found entry with type ${entry.dataKind}")

            entry.dataKind match {
              case ORSetDataKind => println(entry.asORSet.elements)
              case VersionVectorDataKind => println(entry.asVersionVector)
            }
          case None ⇒ println(s"no entry found at key $key")
        }
      case Delete ⇒
        val key = config.key.get
        val success = futureHelper.get(client.removeEntry(key))

        if (success) println(s"successfully deleted entry $key")
        else println(s"couldn't find entry $key")
      case Modify ⇒
        val key = config.key.get
        val op = config.op.get match {
          case "add"    ⇒ AddOpKind
          case "remove" ⇒ RemoveOpKind
          case "clear"  ⇒ ClearOpKind
        }
        val v: Option[Any] = config.elementKind match {
          case Some("boolean") ⇒ Some(config.element.get.toBoolean)
          case Some("byte")    ⇒ Some(config.element.get.toByte)
          case Some("int")     ⇒ Some(config.element.get.toInt)
          case Some("long")    ⇒ Some(config.element.get.toLong)
          case Some("double")  ⇒ Some(config.element.get.toDouble)
          case Some("string")  ⇒ Some(config.element.get)
          case _               ⇒ None
        }

        val success = futureHelper.get(client.modifyEntry(key, op, v))

        if (success) println(s"successfully modified $key")
        else println(s"couldn't find entry $key")
      case Hosts ⇒
        val hosts = futureHelper.get(client.getHosts)
        println(s"current hosts are: $hosts")
      case Dump ⇒
        val entries = futureHelper.get(client.entries)
        println(s"found ${entries.size} entries.")
        println("***")
        entries.foreach {
          case (key, entry) =>
            val value = entry.dataKind match {
              case ORSetDataKind => entry.asORSet.elements
              case VersionVectorDataKind => entry.asVersionVector
            }

            println(s"key: $key, value: $value")
        }
    }
  }

}
