package snapple.benchmark

import snapple.benchmark.utils.{BenchmarkArgParser, IOFutureHelper}

import snapple.client.io.SnappleClient

import snapple.finagle.io._
import snapple.finagle.utils.FinagleUtils._

import com.twitter.finagle.stats.InMemoryStatsReceiver

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import scala.collection.mutable.ArraySeq

import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

import java.text.DecimalFormat

object Main {

  val BytesInUUID = 36

  val decimalFormat = new DecimalFormat("##.00")

  def main(args: Array[String]): Unit = {
    muteLogs

    val config = BenchmarkArgParser(args)

    val client = SnappleClient.singleHost(config.host, config.port)

    val futureHelper = IOFutureHelper(config.host, config.port)

    futureHelper.get(client.ping)

    val keys = Vector.tabulate(config.keys)(_.toString)

    for (key <- keys) futureHelper.get(client.createEntry(key, ORSetDataKind, StringElementKind))

    client.disconnect

    val statsReceiver = new InMemoryStatsReceiver()

    val parallel = !config.sequential

    val numberOfClients = if (parallel) config.clients else BenchmarkArgParser.SequentialClients

    val clients = Vector.tabulate(config.clients) { i => SnappleBenchmarkClient(config.host, config.port, statsReceiver, parallel) }

    val requests = if (parallel) config.requests else BenchmarkArgParser.SequentialRequests
    val payloads = Vector.tabulate(config.requests) { i => UUID.randomUUID.toString }

    var clientIdx = 0
    var keysIdx = 0
    val futures = new ArraySeq[Future[Boolean]](requests)

    val start = System.currentTimeMillis
    for (requestIdx <- 0 until requests) {
      val client = clients(clientIdx)
      clientIdx = (clientIdx + 1) % clients.size

      val key = keys(keysIdx)
      keysIdx = (keysIdx + 1) % keys.size

      val payload = payloads(requestIdx)
      if (parallel) futures(requestIdx) = client.modifyEntry(key, AddOpKind, Some(payload))
      else futureHelper.get(client.modifyEntry(key, AddOpKind, Some(payload)))
    }

    if (parallel) futureHelper.get(Future.sequence(futures))

    val totalTime = (System.currentTimeMillis - start).toDouble
    val totalTimeInSeconds = decimalFormat.format(totalTime / 1000)

    val requestsPerSecond = decimalFormat.format((requests / (totalTime / 1000)))

    clients.foreach(_.disconnect)

    val latencies = statsReceiver.stat("request_latency_ms")().sorted.reverse.drop(1)

    val times = (1 to 10).map {
      case t =>
        val v = (latencies.filter(_ <= t).size * 100.toDouble) / (requests - 1)
        (t -> decimalFormat.format(v))
    }

    val threadString = if (parallel) "parallel" else "sequential"

    println("============BENCHMARK============")
    println(s"$requests $threadString requests finished in $totalTimeInSeconds seconds")
    if (parallel) println(s"${config.clients} parallel client(s)")
    println(s"$BytesInUUID bytes payload")
    println(s"${config.keys} unique key(s) used")
    println()
    if (!parallel) {
      println("Request time displayed: ")
      times.foreach {
        case (t, v) =>
          println(s"$v% <= $t millisecond(s)")
      }
    } else {
      println("Not displaying per request time since not realistic because of futures. Please use `--sequential` to see request times.")
    }
    println()
    println(s"$requestsPerSecond requests per second")
  }

}
