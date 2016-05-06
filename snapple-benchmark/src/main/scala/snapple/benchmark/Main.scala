package snapple.benchmark

import snapple.benchmark.utils.{BenchmarkArgParser, IOFutureHelper}

import snapple.client.io.SnappleClient

import snapple.finagle.io._
import snapple.finagle.utils.FinagleUtils._

import com.twitter.finagle.stats.InMemoryStatsReceiver

import org.apache.commons.lang.RandomStringUtils

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import scala.collection.mutable.ArraySeq

import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

import java.text.DecimalFormat

object Main {

  val DecimalFormat = new DecimalFormat("##.00")

  val Get = "GET"
  val Create = "CREATE"
  val Add = "ADD"

  val Methods = Seq(Get, Create, Add, Get, Create, Add)

  def main(args: Array[String]): Unit = {
    muteLogs

    val config = BenchmarkArgParser(args)

    val bytes = config.byteSize

    val parallel = !config.sequential

    val numberOfClients = if (parallel) config.clients else BenchmarkArgParser.SequentialClients

    val requests = if (parallel) config.requests else BenchmarkArgParser.SequentialRequests

    val futureHelper = IOFutureHelper(config.host, config.port)

    val keys = Vector.tabulate(config.keys)(_.toString).toArray
    val keysToBeRemoved = Vector.tabulate(config.requests)(_.toString)

    val payloads: Array[Option[String]] = (Vector.tabulate(config.requests) { i => Some(RandomStringUtils.randomAlphanumeric(bytes)) }).toArray

    for (methodIdx <- 0 until Methods.size) {
      val method = Methods(methodIdx)

      val initClient = SnappleClient.singleHost(config.host, config.port)
      for (key <- keysToBeRemoved) futureHelper.get(initClient.removeEntry(key))

      if (method == Add || method == Get)
        for (key <- keys)
          futureHelper.get(initClient.createEntry(key, ORSetDataKind, StringElementKind))

      if (method == Get) for (key <- keys) {
        val payload = RandomStringUtils.randomAlphanumeric(bytes)
        futureHelper.get(initClient.modifyEntry(key, AddOpKind, Some(payload)))
      }

      initClient.disconnect

      val statsReceiver = new InMemoryStatsReceiver()

      val clients: Array[SnappleBenchmarkClient] = (Vector.tabulate(config.clients) { i => SnappleBenchmarkClient(config.host, config.port, statsReceiver, parallel) }).toArray

      var keysIdx = 0
      var requestIdx = 0
      var clientIdx = 0
      val futures = new ArraySeq[Future[Boolean]](requests)

      val start = System.currentTimeMillis

      while (requestIdx < requests) {
        val key = keys(keysIdx)
        keysIdx = (keysIdx + 1) % keys.size

        val payload = payloads(requestIdx)

        val client = clients(clientIdx)
        clientIdx = (clientIdx + 1) % clients.size

        val future = method match {
          case Create => client.createEntry(requestIdx.toString, ORSetDataKind, StringElementKind)
          case Add => client.modifyEntry(key, AddOpKind, payload)
          case Get => client.entry(key)
        }

        if (parallel) futures(requestIdx) = future
        else futureHelper.get(future)
        requestIdx += 1
      }

      if (parallel) futureHelper.get(Future.sequence(futures))

      val totalTime = (System.currentTimeMillis - start).toDouble

      clients.foreach(_.disconnect)

      val totalTimeInSeconds = DecimalFormat.format(totalTime / 1000)

      val requestsPerSecond = DecimalFormat.format((requests / (totalTime / 1000)))
      val latencies = statsReceiver.stat("request_latency_ms")().sorted.reverse.drop(1)

      val times = (1 to 10).map {
        case t =>
          val v = (latencies.filter(_ <= t).size * 100.toDouble) / (requests - 1)
          (t -> DecimalFormat.format(v))
      }

      val threadString = if (parallel) "parallel" else "sequential"

      if (methodIdx > 2) {
        println("============BENCHMARK============")
        println(s"Benchmark for method $method")
        println(s"$requests $threadString requests finished in $totalTimeInSeconds")
        if (parallel) println(s"${config.clients} parallel client(s)")
        println(s"$bytes bytes payload")
        println(s"${config.keys} unique key(s) used")
        println()
        if (!parallel) {
          println("Request time displayed: ")
          times.foreach {
            case (t, v) => println(s"$v% <= $t millisecond(s)")
          }
        } else {
          println("Not displaying per request time since not realistic because of futures. Please use `--sequential` to see request times.")
        }

        println()
        println(s"$requestsPerSecond requests per second")
        println()
      }
    }

  }

}
