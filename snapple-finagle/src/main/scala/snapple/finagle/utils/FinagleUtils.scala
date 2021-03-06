package snapple.finagle.utils

import com.twitter.util.{Future => TwitterFuture, Throw, Return}

import scala.concurrent.{Future, Promise}

import java.util.logging.LogManager

object FinagleUtils {
  def toScalaFuture[A](twitterFuture: TwitterFuture[A]): Future[A] = {
    val promise = Promise[A]()

    twitterFuture respond {
      case Return(a) => promise success a
      case Throw(e) => promise failure e
    }

    promise.future
  }

  def muteLogs: Unit = {
    LogManager.getLogManager.reset
  }
}
