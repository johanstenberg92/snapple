package snapple.cluster.io

import snapple.finagle.io.{SnappleService, TDataType}

import snapple.finagle.io._

import snapple.finagle.serialization.DataSerializer

import snapple.cluster.{SnappleServer, KeyValueStore}

import grizzled.slf4j.Logger

import com.twitter.finagle.Thrift

import com.twitter.util.Future

import scala.collection.{Map => SMap}

import java.nio.ByteBuffer

case class ReplicaServer(store: KeyValueStore, port: Int, replicaIdentifier: String) {

  private val logger = Logger[this.type]

  private val opHandler: OpHandler = OpHandler(replicaIdentifier)

  private val server = Thrift.serveIface(s"localhost:$port", SnappleServiceHandler(store))

  private case class SnappleServiceHandler(store: KeyValueStore) extends SnappleService[Future] {

    override def ping(): Future[Unit] = Future {
      logger.info("replica server received ping")
    }

    override def propagate(values: SMap[String, TDataType] = Map[String, TDataType]()): Future[Unit] = Future {
      val deserialized = FinagleMethodHelper.parsePropagate(values)
      store.merge(deserialized)
    }

    override def createEntry(key: String, dataKind: String, elementKind: Int): Future[Boolean] = Future {
      store.entry(key) match {
        case None =>
          val entry = FinagleMethodHelper.parseCreateEntry(dataKind, elementKind)
          store.createEntry(key, entry)
        case _ => false
      }
    }

    override def removeEntry(key: String): Future[Boolean] = Future {
      store.removeEntry(key)
    }

    override def getEntry(key: String): Future[TOptionalDataType] = Future {
      FinagleMethodHelper.convertGetEntry(store.entry(key))
    }

    override def modifyEntry(key: String, operation: String, element: ByteBuffer): Future[Boolean] = Future {
      store.entry(key) match {
        case Some(kve) =>
          val op = opHandler.handleOp(kve.dataKind, kve.elementKind, OpKind(operation), element)
          kve.modify(op)
          true
        case None => false
      }
    }

  }

  def shutdown: Unit = {
    logger.info("shutting down replica server")
    server.close()
  }
}
