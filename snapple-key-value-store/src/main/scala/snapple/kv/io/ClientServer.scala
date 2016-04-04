package snapple.kv.io

import snapple.kv.io.thrift.{TDataType, ClientService, TOptionalString, TOptionalElementType, TOptionalDataType}

import grizzled.slf4j.Logger

import org.apache.thrift.server.TNonblockingServer
import org.apache.thrift.transport.TNonblockingServerSocket

case class ClientServer(port: Int) {

  private val logger = Logger[this.type]

  private val handler = new ClientServiceHandler

  private val processor = new ClientService.Processor(handler)

  private val server = {
    val serverTransport = new TNonblockingServerSocket(port)
    val server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor))

    server.serve
    logger.info(s"started client server on port $port")

    server
  }
}

case class ClientServiceHandler() extends ClientService.Iface {

  private val logger = Logger[this.type]

  override def ping(): Unit = {
    logger.info("client server received ping")
  }

  override def createEntry(key: String, dataType: String, elementType: TOptionalString): Boolean = {
    ???
  }

  override def removeEntry(key: String): Boolean = {
    ???
  }

  override def getEntry(key: String): TOptionalDataType = {
    ???
  }

  override def modifyEntry(key: String, operation: String, element: TOptionalElementType): Boolean = {
    ???
  }

}
