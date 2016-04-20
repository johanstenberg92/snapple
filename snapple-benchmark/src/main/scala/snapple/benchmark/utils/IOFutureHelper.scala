package snapple.benchmark.utils

import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration

import scala.util.{Success, Failure}

case class IOFutureHelper(host: String, port: Int) {

  def get[T](future: Future[T]): T = Await.ready(future, Duration.Inf).value.get match {
    case Success(v) ⇒ v
    case Failure(e) ⇒
      println(e)
      println(s"Couldn't connect to $host:$port")
      sys.exit(-1)
  }
}
