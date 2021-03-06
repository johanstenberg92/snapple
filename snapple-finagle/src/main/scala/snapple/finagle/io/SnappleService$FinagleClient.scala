/**
 * Generated by Scrooge
 *   version: 4.6.0
 *   rev: eb57ae5452b3a7a1fe18ba3ebf6bcc0721393d8c
 *   built at: 20160419-093119
 */
package snapple.finagle.io

import com.twitter.finagle.SourcedException
import com.twitter.finagle.{service => ctfs}
import com.twitter.finagle.stats.{NullStatsReceiver, StatsReceiver}
import com.twitter.finagle.thrift.{Protocols, ThriftClientRequest}
import com.twitter.scrooge.{ThriftStruct, ThriftStructCodec}
import com.twitter.util.{Future, Return, Throw, Throwables}
import java.nio.ByteBuffer
import java.util.Arrays
import org.apache.thrift.protocol._
import org.apache.thrift.TApplicationException
import org.apache.thrift.transport.{TMemoryBuffer, TMemoryInputTransport}
import scala.collection.{Map, Set}
import scala.language.higherKinds


@javax.annotation.Generated(value = Array("com.twitter.scrooge.Compiler"))
class SnappleService$FinagleClient(
    val service: com.twitter.finagle.Service[ThriftClientRequest, Array[Byte]],
    val protocolFactory: TProtocolFactory,
    val serviceName: String,
    stats: StatsReceiver,
    responseClassifier: ctfs.ResponseClassifier)
  extends SnappleService[Future] {

  def this(
    service: com.twitter.finagle.Service[ThriftClientRequest, Array[Byte]],
    protocolFactory: TProtocolFactory = Protocols.binaryFactory(),
    serviceName: String = "SnappleService",
    stats: StatsReceiver = NullStatsReceiver
  ) = this(
    service,
    protocolFactory,
    serviceName,
    stats,
    ctfs.ResponseClassifier.Default
  )

  import SnappleService._

  protected def encodeRequest(name: String, args: ThriftStruct): ThriftClientRequest = {
    val buf = new TMemoryBuffer(512)
    val oprot = protocolFactory.getProtocol(buf)

    oprot.writeMessageBegin(new TMessage(name, TMessageType.CALL, 0))
    args.write(oprot)
    oprot.writeMessageEnd()

    val bytes = Arrays.copyOfRange(buf.getArray, 0, buf.length)
    new ThriftClientRequest(bytes, false)
  }

  protected def decodeResponse[T <: ThriftStruct](
    resBytes: Array[Byte],
    codec: ThriftStructCodec[T]
  ): T = {
    val iprot = protocolFactory.getProtocol(new TMemoryInputTransport(resBytes))
    val msg = iprot.readMessageBegin()
    try {
      if (msg.`type` == TMessageType.EXCEPTION) {
        val exception = TApplicationException.read(iprot) match {
          case sourced: SourcedException =>
            if (serviceName != "") sourced.serviceName = serviceName
            sourced
          case e => e
        }
        throw exception
      } else {
        codec.decode(iprot)
      }
    } finally {
      iprot.readMessageEnd()
    }
  }

  protected def missingResult(name: String) = {
    new TApplicationException(
      TApplicationException.MISSING_RESULT,
      name + " failed: unknown result"
    )
  }

  protected def setServiceName(ex: Throwable): Throwable =
    if (this.serviceName == "") ex
    else {
      ex match {
        case se: SourcedException =>
          se.serviceName = this.serviceName
          se
        case _ => ex
      }
    }

  // ----- end boilerplate.

  private[this] val scopedStats = if (serviceName != "") stats.scope(serviceName) else stats
  private[this] object __stats_ping {
    val RequestsCounter = scopedStats.scope("ping").counter("requests")
    val SuccessCounter = scopedStats.scope("ping").counter("success")
    val FailuresCounter = scopedStats.scope("ping").counter("failures")
    val FailuresScope = scopedStats.scope("ping").scope("failures")
  }
  
  def ping(): Future[Unit] = {
    __stats_ping.RequestsCounter.incr()
    val inputArgs = Ping.Args()
    val replyDeserializer: Array[Byte] => _root_.com.twitter.util.Try[Unit] =
      response => {
        val decodeResult: _root_.com.twitter.util.Try[Ping.Result] =
          _root_.com.twitter.util.Try {
            decodeResponse(response, Ping.Result)
          }
  
        decodeResult match {
          case t@_root_.com.twitter.util.Throw(_) =>
            t.cast[Unit]
          case  _root_.com.twitter.util.Return(result) =>
            val serviceException: Throwable =
              null
  
            if (serviceException != null) _root_.com.twitter.util.Throw(serviceException)
            else _root_.com.twitter.util.Return.Unit
        }
      }
  
    val serdeCtx = new _root_.com.twitter.finagle.thrift.DeserializeCtx[Unit](inputArgs, replyDeserializer)
    _root_.com.twitter.finagle.context.Contexts.local.let(
      _root_.com.twitter.finagle.thrift.DeserializeCtx.Key,
      serdeCtx
    ) {
      val serialized = encodeRequest("ping", inputArgs)
      this.service(serialized).flatMap { response =>
        Future.const(serdeCtx.deserialize(response))
      }.respond { response =>
        val responseClass = responseClassifier.applyOrElse(
          ctfs.ReqRep(inputArgs, response),
          ctfs.ResponseClassifier.Default)
        responseClass match {
          case ctfs.ResponseClass.Successful(_) =>
            __stats_ping.SuccessCounter.incr()
          case ctfs.ResponseClass.Failed(_) =>
            __stats_ping.FailuresCounter.incr()
            response match {
              case Throw(ex) =>
                setServiceName(ex)
                __stats_ping.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
              case _ =>
            }
        }
      }
    }
  }
  private[this] object __stats_propagate {
    val RequestsCounter = scopedStats.scope("propagate").counter("requests")
    val SuccessCounter = scopedStats.scope("propagate").counter("success")
    val FailuresCounter = scopedStats.scope("propagate").counter("failures")
    val FailuresScope = scopedStats.scope("propagate").scope("failures")
  }
  
  def propagate(values: Map[String, snapple.finagle.io.TDataType] = Map[String, snapple.finagle.io.TDataType]()): Future[Unit] = {
    __stats_propagate.RequestsCounter.incr()
    val inputArgs = Propagate.Args(values)
    val replyDeserializer: Array[Byte] => _root_.com.twitter.util.Try[Unit] =
      response => {
        val decodeResult: _root_.com.twitter.util.Try[Propagate.Result] =
          _root_.com.twitter.util.Try {
            decodeResponse(response, Propagate.Result)
          }
  
        decodeResult match {
          case t@_root_.com.twitter.util.Throw(_) =>
            t.cast[Unit]
          case  _root_.com.twitter.util.Return(result) =>
            val serviceException: Throwable =
              null
  
            if (serviceException != null) _root_.com.twitter.util.Throw(serviceException)
            else _root_.com.twitter.util.Return.Unit
        }
      }
  
    val serdeCtx = new _root_.com.twitter.finagle.thrift.DeserializeCtx[Unit](inputArgs, replyDeserializer)
    _root_.com.twitter.finagle.context.Contexts.local.let(
      _root_.com.twitter.finagle.thrift.DeserializeCtx.Key,
      serdeCtx
    ) {
      val serialized = encodeRequest("propagate", inputArgs)
      this.service(serialized).flatMap { response =>
        Future.const(serdeCtx.deserialize(response))
      }.respond { response =>
        val responseClass = responseClassifier.applyOrElse(
          ctfs.ReqRep(inputArgs, response),
          ctfs.ResponseClassifier.Default)
        responseClass match {
          case ctfs.ResponseClass.Successful(_) =>
            __stats_propagate.SuccessCounter.incr()
          case ctfs.ResponseClass.Failed(_) =>
            __stats_propagate.FailuresCounter.incr()
            response match {
              case Throw(ex) =>
                setServiceName(ex)
                __stats_propagate.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
              case _ =>
            }
        }
      }
    }
  }
  private[this] object __stats_createEntry {
    val RequestsCounter = scopedStats.scope("createEntry").counter("requests")
    val SuccessCounter = scopedStats.scope("createEntry").counter("success")
    val FailuresCounter = scopedStats.scope("createEntry").counter("failures")
    val FailuresScope = scopedStats.scope("createEntry").scope("failures")
  }
  
  def createEntry(key: String, dataKind: String, elementKind: Int): Future[Boolean] = {
    __stats_createEntry.RequestsCounter.incr()
    val inputArgs = CreateEntry.Args(key, dataKind, elementKind)
    val replyDeserializer: Array[Byte] => _root_.com.twitter.util.Try[Boolean] =
      response => {
        val decodeResult: _root_.com.twitter.util.Try[CreateEntry.Result] =
          _root_.com.twitter.util.Try {
            decodeResponse(response, CreateEntry.Result)
          }
  
        decodeResult match {
          case t@_root_.com.twitter.util.Throw(_) =>
            t.cast[Boolean]
          case  _root_.com.twitter.util.Return(result) =>
            val serviceException: Throwable =
              null
  
            if (result.success.isDefined)
              _root_.com.twitter.util.Return(result.success.get)
            else if (serviceException != null)
              _root_.com.twitter.util.Throw(serviceException)
            else
              _root_.com.twitter.util.Throw(missingResult("createEntry"))
        }
      }
  
    val serdeCtx = new _root_.com.twitter.finagle.thrift.DeserializeCtx[Boolean](inputArgs, replyDeserializer)
    _root_.com.twitter.finagle.context.Contexts.local.let(
      _root_.com.twitter.finagle.thrift.DeserializeCtx.Key,
      serdeCtx
    ) {
      val serialized = encodeRequest("createEntry", inputArgs)
      this.service(serialized).flatMap { response =>
        Future.const(serdeCtx.deserialize(response))
      }.respond { response =>
        val responseClass = responseClassifier.applyOrElse(
          ctfs.ReqRep(inputArgs, response),
          ctfs.ResponseClassifier.Default)
        responseClass match {
          case ctfs.ResponseClass.Successful(_) =>
            __stats_createEntry.SuccessCounter.incr()
          case ctfs.ResponseClass.Failed(_) =>
            __stats_createEntry.FailuresCounter.incr()
            response match {
              case Throw(ex) =>
                setServiceName(ex)
                __stats_createEntry.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
              case _ =>
            }
        }
      }
    }
  }
  private[this] object __stats_removeEntry {
    val RequestsCounter = scopedStats.scope("removeEntry").counter("requests")
    val SuccessCounter = scopedStats.scope("removeEntry").counter("success")
    val FailuresCounter = scopedStats.scope("removeEntry").counter("failures")
    val FailuresScope = scopedStats.scope("removeEntry").scope("failures")
  }
  
  def removeEntry(key: String): Future[Boolean] = {
    __stats_removeEntry.RequestsCounter.incr()
    val inputArgs = RemoveEntry.Args(key)
    val replyDeserializer: Array[Byte] => _root_.com.twitter.util.Try[Boolean] =
      response => {
        val decodeResult: _root_.com.twitter.util.Try[RemoveEntry.Result] =
          _root_.com.twitter.util.Try {
            decodeResponse(response, RemoveEntry.Result)
          }
  
        decodeResult match {
          case t@_root_.com.twitter.util.Throw(_) =>
            t.cast[Boolean]
          case  _root_.com.twitter.util.Return(result) =>
            val serviceException: Throwable =
              null
  
            if (result.success.isDefined)
              _root_.com.twitter.util.Return(result.success.get)
            else if (serviceException != null)
              _root_.com.twitter.util.Throw(serviceException)
            else
              _root_.com.twitter.util.Throw(missingResult("removeEntry"))
        }
      }
  
    val serdeCtx = new _root_.com.twitter.finagle.thrift.DeserializeCtx[Boolean](inputArgs, replyDeserializer)
    _root_.com.twitter.finagle.context.Contexts.local.let(
      _root_.com.twitter.finagle.thrift.DeserializeCtx.Key,
      serdeCtx
    ) {
      val serialized = encodeRequest("removeEntry", inputArgs)
      this.service(serialized).flatMap { response =>
        Future.const(serdeCtx.deserialize(response))
      }.respond { response =>
        val responseClass = responseClassifier.applyOrElse(
          ctfs.ReqRep(inputArgs, response),
          ctfs.ResponseClassifier.Default)
        responseClass match {
          case ctfs.ResponseClass.Successful(_) =>
            __stats_removeEntry.SuccessCounter.incr()
          case ctfs.ResponseClass.Failed(_) =>
            __stats_removeEntry.FailuresCounter.incr()
            response match {
              case Throw(ex) =>
                setServiceName(ex)
                __stats_removeEntry.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
              case _ =>
            }
        }
      }
    }
  }
  private[this] object __stats_getEntry {
    val RequestsCounter = scopedStats.scope("getEntry").counter("requests")
    val SuccessCounter = scopedStats.scope("getEntry").counter("success")
    val FailuresCounter = scopedStats.scope("getEntry").counter("failures")
    val FailuresScope = scopedStats.scope("getEntry").scope("failures")
  }
  
  def getEntry(key: String): Future[snapple.finagle.io.TOptionalDataType] = {
    __stats_getEntry.RequestsCounter.incr()
    val inputArgs = GetEntry.Args(key)
    val replyDeserializer: Array[Byte] => _root_.com.twitter.util.Try[snapple.finagle.io.TOptionalDataType] =
      response => {
        val decodeResult: _root_.com.twitter.util.Try[GetEntry.Result] =
          _root_.com.twitter.util.Try {
            decodeResponse(response, GetEntry.Result)
          }
  
        decodeResult match {
          case t@_root_.com.twitter.util.Throw(_) =>
            t.cast[snapple.finagle.io.TOptionalDataType]
          case  _root_.com.twitter.util.Return(result) =>
            val serviceException: Throwable =
              null
  
            if (result.success.isDefined)
              _root_.com.twitter.util.Return(result.success.get)
            else if (serviceException != null)
              _root_.com.twitter.util.Throw(serviceException)
            else
              _root_.com.twitter.util.Throw(missingResult("getEntry"))
        }
      }
  
    val serdeCtx = new _root_.com.twitter.finagle.thrift.DeserializeCtx[snapple.finagle.io.TOptionalDataType](inputArgs, replyDeserializer)
    _root_.com.twitter.finagle.context.Contexts.local.let(
      _root_.com.twitter.finagle.thrift.DeserializeCtx.Key,
      serdeCtx
    ) {
      val serialized = encodeRequest("getEntry", inputArgs)
      this.service(serialized).flatMap { response =>
        Future.const(serdeCtx.deserialize(response))
      }.respond { response =>
        val responseClass = responseClassifier.applyOrElse(
          ctfs.ReqRep(inputArgs, response),
          ctfs.ResponseClassifier.Default)
        responseClass match {
          case ctfs.ResponseClass.Successful(_) =>
            __stats_getEntry.SuccessCounter.incr()
          case ctfs.ResponseClass.Failed(_) =>
            __stats_getEntry.FailuresCounter.incr()
            response match {
              case Throw(ex) =>
                setServiceName(ex)
                __stats_getEntry.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
              case _ =>
            }
        }
      }
    }
  }
  private[this] object __stats_modifyEntry {
    val RequestsCounter = scopedStats.scope("modifyEntry").counter("requests")
    val SuccessCounter = scopedStats.scope("modifyEntry").counter("success")
    val FailuresCounter = scopedStats.scope("modifyEntry").counter("failures")
    val FailuresScope = scopedStats.scope("modifyEntry").scope("failures")
  }
  
  def modifyEntry(key: String, operation: String, element: ByteBuffer): Future[Boolean] = {
    __stats_modifyEntry.RequestsCounter.incr()
    val inputArgs = ModifyEntry.Args(key, operation, element)
    val replyDeserializer: Array[Byte] => _root_.com.twitter.util.Try[Boolean] =
      response => {
        val decodeResult: _root_.com.twitter.util.Try[ModifyEntry.Result] =
          _root_.com.twitter.util.Try {
            decodeResponse(response, ModifyEntry.Result)
          }
  
        decodeResult match {
          case t@_root_.com.twitter.util.Throw(_) =>
            t.cast[Boolean]
          case  _root_.com.twitter.util.Return(result) =>
            val serviceException: Throwable =
              null
  
            if (result.success.isDefined)
              _root_.com.twitter.util.Return(result.success.get)
            else if (serviceException != null)
              _root_.com.twitter.util.Throw(serviceException)
            else
              _root_.com.twitter.util.Throw(missingResult("modifyEntry"))
        }
      }
  
    val serdeCtx = new _root_.com.twitter.finagle.thrift.DeserializeCtx[Boolean](inputArgs, replyDeserializer)
    _root_.com.twitter.finagle.context.Contexts.local.let(
      _root_.com.twitter.finagle.thrift.DeserializeCtx.Key,
      serdeCtx
    ) {
      val serialized = encodeRequest("modifyEntry", inputArgs)
      this.service(serialized).flatMap { response =>
        Future.const(serdeCtx.deserialize(response))
      }.respond { response =>
        val responseClass = responseClassifier.applyOrElse(
          ctfs.ReqRep(inputArgs, response),
          ctfs.ResponseClassifier.Default)
        responseClass match {
          case ctfs.ResponseClass.Successful(_) =>
            __stats_modifyEntry.SuccessCounter.incr()
          case ctfs.ResponseClass.Failed(_) =>
            __stats_modifyEntry.FailuresCounter.incr()
            response match {
              case Throw(ex) =>
                setServiceName(ex)
                __stats_modifyEntry.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
              case _ =>
            }
        }
      }
    }
  }
  private[this] object __stats_getAllEntries {
    val RequestsCounter = scopedStats.scope("getAllEntries").counter("requests")
    val SuccessCounter = scopedStats.scope("getAllEntries").counter("success")
    val FailuresCounter = scopedStats.scope("getAllEntries").counter("failures")
    val FailuresScope = scopedStats.scope("getAllEntries").scope("failures")
  }
  
  def getAllEntries(): Future[Map[String, snapple.finagle.io.TDataType]] = {
    __stats_getAllEntries.RequestsCounter.incr()
    val inputArgs = GetAllEntries.Args()
    val replyDeserializer: Array[Byte] => _root_.com.twitter.util.Try[Map[String, snapple.finagle.io.TDataType]] =
      response => {
        val decodeResult: _root_.com.twitter.util.Try[GetAllEntries.Result] =
          _root_.com.twitter.util.Try {
            decodeResponse(response, GetAllEntries.Result)
          }
  
        decodeResult match {
          case t@_root_.com.twitter.util.Throw(_) =>
            t.cast[Map[String, snapple.finagle.io.TDataType]]
          case  _root_.com.twitter.util.Return(result) =>
            val serviceException: Throwable =
              null
  
            if (result.success.isDefined)
              _root_.com.twitter.util.Return(result.success.get)
            else if (serviceException != null)
              _root_.com.twitter.util.Throw(serviceException)
            else
              _root_.com.twitter.util.Throw(missingResult("getAllEntries"))
        }
      }
  
    val serdeCtx = new _root_.com.twitter.finagle.thrift.DeserializeCtx[Map[String, snapple.finagle.io.TDataType]](inputArgs, replyDeserializer)
    _root_.com.twitter.finagle.context.Contexts.local.let(
      _root_.com.twitter.finagle.thrift.DeserializeCtx.Key,
      serdeCtx
    ) {
      val serialized = encodeRequest("getAllEntries", inputArgs)
      this.service(serialized).flatMap { response =>
        Future.const(serdeCtx.deserialize(response))
      }.respond { response =>
        val responseClass = responseClassifier.applyOrElse(
          ctfs.ReqRep(inputArgs, response),
          ctfs.ResponseClassifier.Default)
        responseClass match {
          case ctfs.ResponseClass.Successful(_) =>
            __stats_getAllEntries.SuccessCounter.incr()
          case ctfs.ResponseClass.Failed(_) =>
            __stats_getAllEntries.FailuresCounter.incr()
            response match {
              case Throw(ex) =>
                setServiceName(ex)
                __stats_getAllEntries.FailuresScope.counter(Throwables.mkString(ex): _*).incr()
              case _ =>
            }
        }
      }
    }
  }
}
