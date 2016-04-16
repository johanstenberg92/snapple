package snapple.cluster

import snapple.cluster.utils.ArgParser

object Main {

  def main(args: Array[String]): Unit = {
    val config = ArgParser(args)

    SnappleServer.run(config)
  }
}
