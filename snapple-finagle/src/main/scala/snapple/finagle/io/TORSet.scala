/**
 * Generated by Scrooge
 *   version: 4.6.0
 *   rev: eb57ae5452b3a7a1fe18ba3ebf6bcc0721393d8c
 *   built at: 20160419-093119
 */
package snapple.finagle.io

import com.twitter.scrooge.{
  LazyTProtocol,
  TFieldBlob, ThriftException, ThriftStruct, ThriftStructCodec3, ThriftStructFieldInfo,
  ThriftStructMetaData, ThriftUtil}
import org.apache.thrift.protocol._
import org.apache.thrift.transport.{TMemoryBuffer, TTransport}
import java.nio.ByteBuffer
import java.util.Arrays
import scala.collection.immutable.{Map => immutable$Map}
import scala.collection.mutable.Builder
import scala.collection.mutable.{
  ArrayBuffer => mutable$ArrayBuffer, Buffer => mutable$Buffer,
  HashMap => mutable$HashMap, HashSet => mutable$HashSet}
import scala.collection.{Map, Set}


object TORSet extends ThriftStructCodec3[TORSet] {
  private val NoPassthroughFields = immutable$Map.empty[Short, TFieldBlob]
  val Struct = new TStruct("TORSet")
  val ElementKindField = new TField("elementKind", TType.I32, 1)
  val ElementKindFieldManifest = implicitly[Manifest[Int]]
  val ElementsField = new TField("elements", TType.MAP, 2)
  val ElementsFieldManifest = implicitly[Manifest[Map[ByteBuffer, snapple.finagle.io.TVersionVector]]]
  val VersionVectorField = new TField("versionVector", TType.STRUCT, 4)
  val VersionVectorFieldManifest = implicitly[Manifest[snapple.finagle.io.TVersionVector]]

  /**
   * Field information in declaration order.
   */
  lazy val fieldInfos: scala.List[ThriftStructFieldInfo] = scala.List[ThriftStructFieldInfo](
    new ThriftStructFieldInfo(
      ElementKindField,
      false,
      false,
      ElementKindFieldManifest,
      _root_.scala.None,
      _root_.scala.None,
      immutable$Map.empty[String, String],
      immutable$Map.empty[String, String]
    ),
    new ThriftStructFieldInfo(
      ElementsField,
      false,
      false,
      ElementsFieldManifest,
      _root_.scala.Some(implicitly[Manifest[ByteBuffer]]),
      _root_.scala.Some(implicitly[Manifest[snapple.finagle.io.TVersionVector]]),
      immutable$Map.empty[String, String],
      immutable$Map.empty[String, String]
    ),
    new ThriftStructFieldInfo(
      VersionVectorField,
      false,
      false,
      VersionVectorFieldManifest,
      _root_.scala.None,
      _root_.scala.None,
      immutable$Map.empty[String, String],
      immutable$Map.empty[String, String]
    )
  )

  lazy val structAnnotations: immutable$Map[String, String] =
    immutable$Map.empty[String, String]

  /**
   * Checks that all required fields are non-null.
   */
  def validate(_item: TORSet): Unit = {
  }

  def withoutPassthroughFields(original: TORSet): TORSet =
    new Immutable(
      elementKind =
        {
          val field = original.elementKind
          field
        },
      elements =
        {
          val field = original.elements
          field.map { case (key, value) =>
              val newKey = {
              val field = key
              field
            }
  
          
              val newValue = {
              val field = value
              snapple.finagle.io.TVersionVector.withoutPassthroughFields(field)
            }
  
          
            newKey -> newValue
          }
        },
      versionVector =
        {
          val field = original.versionVector
          snapple.finagle.io.TVersionVector.withoutPassthroughFields(field)
        }
    )

  override def encode(_item: TORSet, _oproto: TProtocol): Unit = {
    _item.write(_oproto)
  }

  private[this] def lazyDecode(_iprot: LazyTProtocol): TORSet = {

    var elementKind: Int = 0
    var elements: Map[ByteBuffer, snapple.finagle.io.TVersionVector] = Map[ByteBuffer, snapple.finagle.io.TVersionVector]()
    var versionVector: snapple.finagle.io.TVersionVector = null

    var _passthroughFields: Builder[(Short, TFieldBlob), immutable$Map[Short, TFieldBlob]] = null
    var _done = false
    val _start_offset = _iprot.offset

    _iprot.readStructBegin()
    while (!_done) {
      val _field = _iprot.readFieldBegin()
      if (_field.`type` == TType.STOP) {
        _done = true
      } else {
        _field.id match {
          case 1 =>
            _field.`type` match {
              case TType.I32 =>
    
                elementKind = readElementKindValue(_iprot)
              case _actualType =>
                val _expectedType = TType.I32
                throw new TProtocolException(
                  "Received wrong type for field 'elementKind' (expected=%s, actual=%s).".format(
                    ttypeToString(_expectedType),
                    ttypeToString(_actualType)
                  )
                )
            }
          case 2 =>
            _field.`type` match {
              case TType.MAP =>
    
                elements = readElementsValue(_iprot)
              case _actualType =>
                val _expectedType = TType.MAP
                throw new TProtocolException(
                  "Received wrong type for field 'elements' (expected=%s, actual=%s).".format(
                    ttypeToString(_expectedType),
                    ttypeToString(_actualType)
                  )
                )
            }
          case 4 =>
            _field.`type` match {
              case TType.STRUCT =>
    
                versionVector = readVersionVectorValue(_iprot)
              case _actualType =>
                val _expectedType = TType.STRUCT
                throw new TProtocolException(
                  "Received wrong type for field 'versionVector' (expected=%s, actual=%s).".format(
                    ttypeToString(_expectedType),
                    ttypeToString(_actualType)
                  )
                )
            }
          case _ =>
            if (_passthroughFields == null)
              _passthroughFields = immutable$Map.newBuilder[Short, TFieldBlob]
            _passthroughFields += (_field.id -> TFieldBlob.read(_field, _iprot))
        }
        _iprot.readFieldEnd()
      }
    }
    _iprot.readStructEnd()

    new LazyImmutable(
      _iprot,
      _iprot.buffer,
      _start_offset,
      _iprot.offset,
      elementKind,
      elements,
      versionVector,
      if (_passthroughFields == null)
        NoPassthroughFields
      else
        _passthroughFields.result()
    )
  }

  override def decode(_iprot: TProtocol): TORSet =
    _iprot match {
      case i: LazyTProtocol => lazyDecode(i)
      case i => eagerDecode(i)
    }

  private[this] def eagerDecode(_iprot: TProtocol): TORSet = {
    var elementKind: Int = 0
    var elements: Map[ByteBuffer, snapple.finagle.io.TVersionVector] = Map[ByteBuffer, snapple.finagle.io.TVersionVector]()
    var versionVector: snapple.finagle.io.TVersionVector = null
    var _passthroughFields: Builder[(Short, TFieldBlob), immutable$Map[Short, TFieldBlob]] = null
    var _done = false

    _iprot.readStructBegin()
    while (!_done) {
      val _field = _iprot.readFieldBegin()
      if (_field.`type` == TType.STOP) {
        _done = true
      } else {
        _field.id match {
          case 1 =>
            _field.`type` match {
              case TType.I32 =>
                elementKind = readElementKindValue(_iprot)
              case _actualType =>
                val _expectedType = TType.I32
                throw new TProtocolException(
                  "Received wrong type for field 'elementKind' (expected=%s, actual=%s).".format(
                    ttypeToString(_expectedType),
                    ttypeToString(_actualType)
                  )
                )
            }
          case 2 =>
            _field.`type` match {
              case TType.MAP =>
                elements = readElementsValue(_iprot)
              case _actualType =>
                val _expectedType = TType.MAP
                throw new TProtocolException(
                  "Received wrong type for field 'elements' (expected=%s, actual=%s).".format(
                    ttypeToString(_expectedType),
                    ttypeToString(_actualType)
                  )
                )
            }
          case 4 =>
            _field.`type` match {
              case TType.STRUCT =>
                versionVector = readVersionVectorValue(_iprot)
              case _actualType =>
                val _expectedType = TType.STRUCT
                throw new TProtocolException(
                  "Received wrong type for field 'versionVector' (expected=%s, actual=%s).".format(
                    ttypeToString(_expectedType),
                    ttypeToString(_actualType)
                  )
                )
            }
          case _ =>
            if (_passthroughFields == null)
              _passthroughFields = immutable$Map.newBuilder[Short, TFieldBlob]
            _passthroughFields += (_field.id -> TFieldBlob.read(_field, _iprot))
        }
        _iprot.readFieldEnd()
      }
    }
    _iprot.readStructEnd()

    new Immutable(
      elementKind,
      elements,
      versionVector,
      if (_passthroughFields == null)
        NoPassthroughFields
      else
        _passthroughFields.result()
    )
  }

  def apply(
    elementKind: Int,
    elements: Map[ByteBuffer, snapple.finagle.io.TVersionVector] = Map[ByteBuffer, snapple.finagle.io.TVersionVector](),
    versionVector: snapple.finagle.io.TVersionVector
  ): TORSet =
    new Immutable(
      elementKind,
      elements,
      versionVector
    )

  def unapply(_item: TORSet): _root_.scala.Option[scala.Product3[Int, Map[ByteBuffer, snapple.finagle.io.TVersionVector], snapple.finagle.io.TVersionVector]] = _root_.scala.Some(_item)


  @inline private def readElementKindValue(_iprot: TProtocol): Int = {
    _iprot.readI32()
  }

  @inline private def writeElementKindField(elementKind_item: Int, _oprot: TProtocol): Unit = {
    _oprot.writeFieldBegin(ElementKindField)
    writeElementKindValue(elementKind_item, _oprot)
    _oprot.writeFieldEnd()
  }

  @inline private def writeElementKindValue(elementKind_item: Int, _oprot: TProtocol): Unit = {
    _oprot.writeI32(elementKind_item)
  }

  @inline private def readElementsValue(_iprot: TProtocol): Map[ByteBuffer, snapple.finagle.io.TVersionVector] = {
    val _map = _iprot.readMapBegin()
    if (_map.size == 0) {
      _iprot.readMapEnd()
      Map.empty[ByteBuffer, snapple.finagle.io.TVersionVector]
    } else {
      val _rv = new mutable$HashMap[ByteBuffer, snapple.finagle.io.TVersionVector]
      var _i = 0
      while (_i < _map.size) {
        val _key = {
          _iprot.readBinary()
        }
        val _value = {
          snapple.finagle.io.TVersionVector.decode(_iprot)
        }
        _rv(_key) = _value
        _i += 1
      }
      _iprot.readMapEnd()
      _rv
    }
  }

  @inline private def writeElementsField(elements_item: Map[ByteBuffer, snapple.finagle.io.TVersionVector], _oprot: TProtocol): Unit = {
    _oprot.writeFieldBegin(ElementsField)
    writeElementsValue(elements_item, _oprot)
    _oprot.writeFieldEnd()
  }

  @inline private def writeElementsValue(elements_item: Map[ByteBuffer, snapple.finagle.io.TVersionVector], _oprot: TProtocol): Unit = {
    _oprot.writeMapBegin(new TMap(TType.STRING, TType.STRUCT, elements_item.size))
    elements_item.foreach { case (elements_item_key, elements_item_value) =>
      _oprot.writeBinary(elements_item_key)
      elements_item_value.write(_oprot)
    }
    _oprot.writeMapEnd()
  }

  @inline private def readVersionVectorValue(_iprot: TProtocol): snapple.finagle.io.TVersionVector = {
    snapple.finagle.io.TVersionVector.decode(_iprot)
  }

  @inline private def writeVersionVectorField(versionVector_item: snapple.finagle.io.TVersionVector, _oprot: TProtocol): Unit = {
    _oprot.writeFieldBegin(VersionVectorField)
    writeVersionVectorValue(versionVector_item, _oprot)
    _oprot.writeFieldEnd()
  }

  @inline private def writeVersionVectorValue(versionVector_item: snapple.finagle.io.TVersionVector, _oprot: TProtocol): Unit = {
    versionVector_item.write(_oprot)
  }


  object Immutable extends ThriftStructCodec3[TORSet] {
    override def encode(_item: TORSet, _oproto: TProtocol): Unit = { _item.write(_oproto) }
    override def decode(_iprot: TProtocol): TORSet = TORSet.decode(_iprot)
    override lazy val metaData: ThriftStructMetaData[TORSet] = TORSet.metaData
  }

  /**
   * The default read-only implementation of TORSet.  You typically should not need to
   * directly reference this class; instead, use the TORSet.apply method to construct
   * new instances.
   */
  class Immutable(
      val elementKind: Int,
      val elements: Map[ByteBuffer, snapple.finagle.io.TVersionVector],
      val versionVector: snapple.finagle.io.TVersionVector,
      override val _passthroughFields: immutable$Map[Short, TFieldBlob])
    extends TORSet {
    def this(
      elementKind: Int,
      elements: Map[ByteBuffer, snapple.finagle.io.TVersionVector] = Map[ByteBuffer, snapple.finagle.io.TVersionVector](),
      versionVector: snapple.finagle.io.TVersionVector
    ) = this(
      elementKind,
      elements,
      versionVector,
      Map.empty
    )
  }

  /**
   * This is another Immutable, this however keeps strings as lazy values that are lazily decoded from the backing
   * array byte on read.
   */
  private[this] class LazyImmutable(
      _proto: LazyTProtocol,
      _buf: Array[Byte],
      _start_offset: Int,
      _end_offset: Int,
      val elementKind: Int,
      val elements: Map[ByteBuffer, snapple.finagle.io.TVersionVector],
      val versionVector: snapple.finagle.io.TVersionVector,
      override val _passthroughFields: immutable$Map[Short, TFieldBlob])
    extends TORSet {

    override def write(_oprot: TProtocol): Unit = {
      _oprot match {
        case i: LazyTProtocol => i.writeRaw(_buf, _start_offset, _end_offset - _start_offset)
        case _ => super.write(_oprot)
      }
    }


    /**
     * Override the super hash code to make it a lazy val rather than def.
     *
     * Calculating the hash code can be expensive, caching it where possible
     * can provide significant performance wins. (Key in a hash map for instance)
     * Usually not safe since the normal constructor will accept a mutable map or
     * set as an arg
     * Here however we control how the class is generated from serialized data.
     * With the class private and the contract that we throw away our mutable references
     * having the hash code lazy here is safe.
     */
    override lazy val hashCode = super.hashCode
  }

  /**
   * This Proxy trait allows you to extend the TORSet trait with additional state or
   * behavior and implement the read-only methods from TORSet using an underlying
   * instance.
   */
  trait Proxy extends TORSet {
    protected def _underlying_TORSet: TORSet
    override def elementKind: Int = _underlying_TORSet.elementKind
    override def elements: Map[ByteBuffer, snapple.finagle.io.TVersionVector] = _underlying_TORSet.elements
    override def versionVector: snapple.finagle.io.TVersionVector = _underlying_TORSet.versionVector
    override def _passthroughFields = _underlying_TORSet._passthroughFields
  }
}

trait TORSet
  extends ThriftStruct
  with scala.Product3[Int, Map[ByteBuffer, snapple.finagle.io.TVersionVector], snapple.finagle.io.TVersionVector]
  with java.io.Serializable
{
  import TORSet._

  def elementKind: Int
  def elements: Map[ByteBuffer, snapple.finagle.io.TVersionVector]
  def versionVector: snapple.finagle.io.TVersionVector

  def _passthroughFields: immutable$Map[Short, TFieldBlob] = immutable$Map.empty

  def _1 = elementKind
  def _2 = elements
  def _3 = versionVector


  /**
   * Gets a field value encoded as a binary blob using TCompactProtocol.  If the specified field
   * is present in the passthrough map, that value is returned.  Otherwise, if the specified field
   * is known and not optional and set to None, then the field is serialized and returned.
   */
  def getFieldBlob(_fieldId: Short): _root_.scala.Option[TFieldBlob] = {
    lazy val _buff = new TMemoryBuffer(32)
    lazy val _oprot = new TCompactProtocol(_buff)
    _passthroughFields.get(_fieldId) match {
      case blob: _root_.scala.Some[TFieldBlob] => blob
      case _root_.scala.None => {
        val _fieldOpt: _root_.scala.Option[TField] =
          _fieldId match {
            case 1 =>
              if (true) {
                writeElementKindValue(elementKind, _oprot)
                _root_.scala.Some(TORSet.ElementKindField)
              } else {
                _root_.scala.None
              }
            case 2 =>
              if (elements ne null) {
                writeElementsValue(elements, _oprot)
                _root_.scala.Some(TORSet.ElementsField)
              } else {
                _root_.scala.None
              }
            case 4 =>
              if (versionVector ne null) {
                writeVersionVectorValue(versionVector, _oprot)
                _root_.scala.Some(TORSet.VersionVectorField)
              } else {
                _root_.scala.None
              }
            case _ => _root_.scala.None
          }
        _fieldOpt match {
          case _root_.scala.Some(_field) =>
            val _data = Arrays.copyOfRange(_buff.getArray, 0, _buff.length)
            _root_.scala.Some(TFieldBlob(_field, _data))
          case _root_.scala.None =>
            _root_.scala.None
        }
      }
    }
  }

  /**
   * Collects TCompactProtocol-encoded field values according to `getFieldBlob` into a map.
   */
  def getFieldBlobs(ids: TraversableOnce[Short]): immutable$Map[Short, TFieldBlob] =
    (ids flatMap { id => getFieldBlob(id) map { id -> _ } }).toMap

  /**
   * Sets a field using a TCompactProtocol-encoded binary blob.  If the field is a known
   * field, the blob is decoded and the field is set to the decoded value.  If the field
   * is unknown and passthrough fields are enabled, then the blob will be stored in
   * _passthroughFields.
   */
  def setField(_blob: TFieldBlob): TORSet = {
    var elementKind: Int = this.elementKind
    var elements: Map[ByteBuffer, snapple.finagle.io.TVersionVector] = this.elements
    var versionVector: snapple.finagle.io.TVersionVector = this.versionVector
    var _passthroughFields = this._passthroughFields
    _blob.id match {
      case 1 =>
        elementKind = readElementKindValue(_blob.read)
      case 2 =>
        elements = readElementsValue(_blob.read)
      case 4 =>
        versionVector = readVersionVectorValue(_blob.read)
      case _ => _passthroughFields += (_blob.id -> _blob)
    }
    new Immutable(
      elementKind,
      elements,
      versionVector,
      _passthroughFields
    )
  }

  /**
   * If the specified field is optional, it is set to None.  Otherwise, if the field is
   * known, it is reverted to its default value; if the field is unknown, it is removed
   * from the passthroughFields map, if present.
   */
  def unsetField(_fieldId: Short): TORSet = {
    var elementKind: Int = this.elementKind
    var elements: Map[ByteBuffer, snapple.finagle.io.TVersionVector] = this.elements
    var versionVector: snapple.finagle.io.TVersionVector = this.versionVector

    _fieldId match {
      case 1 =>
        elementKind = 0
      case 2 =>
        elements = Map[ByteBuffer, snapple.finagle.io.TVersionVector]()
      case 4 =>
        versionVector = null
      case _ =>
    }
    new Immutable(
      elementKind,
      elements,
      versionVector,
      _passthroughFields - _fieldId
    )
  }

  /**
   * If the specified field is optional, it is set to None.  Otherwise, if the field is
   * known, it is reverted to its default value; if the field is unknown, it is removed
   * from the passthroughFields map, if present.
   */
  def unsetElementKind: TORSet = unsetField(1)

  def unsetElements: TORSet = unsetField(2)

  def unsetVersionVector: TORSet = unsetField(4)


  override def write(_oprot: TProtocol): Unit = {
    TORSet.validate(this)
    _oprot.writeStructBegin(Struct)
    writeElementKindField(elementKind, _oprot)
    if (elements ne null) writeElementsField(elements, _oprot)
    if (versionVector ne null) writeVersionVectorField(versionVector, _oprot)
    if (_passthroughFields.nonEmpty) {
      _passthroughFields.values.foreach { _.write(_oprot) }
    }
    _oprot.writeFieldStop()
    _oprot.writeStructEnd()
  }

  def copy(
    elementKind: Int = this.elementKind,
    elements: Map[ByteBuffer, snapple.finagle.io.TVersionVector] = this.elements,
    versionVector: snapple.finagle.io.TVersionVector = this.versionVector,
    _passthroughFields: immutable$Map[Short, TFieldBlob] = this._passthroughFields
  ): TORSet =
    new Immutable(
      elementKind,
      elements,
      versionVector,
      _passthroughFields
    )

  override def canEqual(other: Any): Boolean = other.isInstanceOf[TORSet]

  override def equals(other: Any): Boolean =
    canEqual(other) &&
      _root_.scala.runtime.ScalaRunTime._equals(this, other) &&
      _passthroughFields == other.asInstanceOf[TORSet]._passthroughFields

  override def hashCode: Int = _root_.scala.runtime.ScalaRunTime._hashCode(this)

  override def toString: String = _root_.scala.runtime.ScalaRunTime._toString(this)


  override def productArity: Int = 3

  override def productElement(n: Int): Any = n match {
    case 0 => this.elementKind
    case 1 => this.elements
    case 2 => this.versionVector
    case _ => throw new IndexOutOfBoundsException(n.toString)
  }

  override def productPrefix: String = "TORSet"
}