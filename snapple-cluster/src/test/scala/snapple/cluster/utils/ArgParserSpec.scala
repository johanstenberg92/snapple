package snapple.cluster.utils

import org.scalatest.{WordSpecLike, Matchers}

class ArgParserSpec extends WordSpecLike with Matchers {

  "Arguments" must {

    "be parsed correctly" in {
      val args = Array("-p", "9001", "-i", "mustafackim", "-r", "192.168.0.1:1337, 192.168.0.2:1338", "-s", "12")

      ArgParser(args) should be (Configuration(9001, "mustafackim", Seq(("192.168.0.1", 1337), ("192.168.0.2", 1338)), 12))
    }

    "have correct defaults" in {
      var args = Array[String]()

      ArgParser(args).port should be (ArgParser.DefaultPort)
      ArgParser(args).propagationInterval should be (ArgParser.DefaultPropagationInterval)
    }

    "parse optional arguments" in {
      var args = Array("-i", "mustafackim", "-r", "192.168.0.1:1337")

      ArgParser(args) should be (Configuration(9000, "mustafackim", Seq(("192.168.0.1", 1337))))
    }
  }
}
