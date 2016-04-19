package snapple.cli.utils

import org.scalatest.{WordSpecLike, Matchers}

class CLIArgParserSpec extends WordSpecLike with Matchers {

  "CLI Arguments" must {

    "be parsed correctly" in {
      val args = Array("-h", "hello", "-p", "9001")

      CLIArgParser(args) should be (CLIConfiguration("hello", 9001))
    }

    "have correct defaults" in {
      var args = Array[String]()

      CLIArgParser(args).host should be (CLIArgParser.DefaultHost)
      CLIArgParser(args).port should be (CLIArgParser.DefaultPort)
    }
  }
}
