/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package snapple.thrift.io;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-04-14")
public class TORSet implements org.apache.thrift.TBase<TORSet, TORSet._Fields>, java.io.Serializable, Cloneable, Comparable<TORSet> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TORSet");

  private static final org.apache.thrift.protocol.TField ELEMENT_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("elementType", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField ELEMENT_KEYS_FIELD_DESC = new org.apache.thrift.protocol.TField("elementKeys", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField ELEMENT_VALUES_FIELD_DESC = new org.apache.thrift.protocol.TField("elementValues", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField VERSION_VECTOR_FIELD_DESC = new org.apache.thrift.protocol.TField("versionVector", org.apache.thrift.protocol.TType.STRUCT, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TORSetStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TORSetTupleSchemeFactory());
  }

  public int elementType; // required
  public List<ByteBuffer> elementKeys; // required
  public List<TVersionVector> elementValues; // required
  public TVersionVector versionVector; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ELEMENT_TYPE((short)1, "elementType"),
    ELEMENT_KEYS((short)2, "elementKeys"),
    ELEMENT_VALUES((short)3, "elementValues"),
    VERSION_VECTOR((short)4, "versionVector");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ELEMENT_TYPE
          return ELEMENT_TYPE;
        case 2: // ELEMENT_KEYS
          return ELEMENT_KEYS;
        case 3: // ELEMENT_VALUES
          return ELEMENT_VALUES;
        case 4: // VERSION_VECTOR
          return VERSION_VECTOR;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ELEMENTTYPE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ELEMENT_TYPE, new org.apache.thrift.meta_data.FieldMetaData("elementType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ELEMENT_KEYS, new org.apache.thrift.meta_data.FieldMetaData("elementKeys", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING            , true))));
    tmpMap.put(_Fields.ELEMENT_VALUES, new org.apache.thrift.meta_data.FieldMetaData("elementValues", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "TVersionVector"))));
    tmpMap.put(_Fields.VERSION_VECTOR, new org.apache.thrift.meta_data.FieldMetaData("versionVector", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT        , "TVersionVector")));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TORSet.class, metaDataMap);
  }

  public TORSet() {
  }

  public TORSet(
    int elementType,
    List<ByteBuffer> elementKeys,
    List<TVersionVector> elementValues,
    TVersionVector versionVector)
  {
    this();
    this.elementType = elementType;
    setElementTypeIsSet(true);
    this.elementKeys = elementKeys;
    this.elementValues = elementValues;
    this.versionVector = versionVector;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TORSet(TORSet other) {
    __isset_bitfield = other.__isset_bitfield;
    this.elementType = other.elementType;
    if (other.isSetElementKeys()) {
      List<ByteBuffer> __this__elementKeys = new ArrayList<ByteBuffer>(other.elementKeys);
      this.elementKeys = __this__elementKeys;
    }
    if (other.isSetElementValues()) {
      List<TVersionVector> __this__elementValues = new ArrayList<TVersionVector>(other.elementValues.size());
      for (TVersionVector other_element : other.elementValues) {
        __this__elementValues.add(other_element);
      }
      this.elementValues = __this__elementValues;
    }
    if (other.isSetVersionVector()) {
      this.versionVector = other.versionVector;
    }
  }

  public TORSet deepCopy() {
    return new TORSet(this);
  }

  @Override
  public void clear() {
    setElementTypeIsSet(false);
    this.elementType = 0;
    this.elementKeys = null;
    this.elementValues = null;
    this.versionVector = null;
  }

  public int getElementType() {
    return this.elementType;
  }

  public TORSet setElementType(int elementType) {
    this.elementType = elementType;
    setElementTypeIsSet(true);
    return this;
  }

  public void unsetElementType() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ELEMENTTYPE_ISSET_ID);
  }

  /** Returns true if field elementType is set (has been assigned a value) and false otherwise */
  public boolean isSetElementType() {
    return EncodingUtils.testBit(__isset_bitfield, __ELEMENTTYPE_ISSET_ID);
  }

  public void setElementTypeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ELEMENTTYPE_ISSET_ID, value);
  }

  public int getElementKeysSize() {
    return (this.elementKeys == null) ? 0 : this.elementKeys.size();
  }

  public java.util.Iterator<ByteBuffer> getElementKeysIterator() {
    return (this.elementKeys == null) ? null : this.elementKeys.iterator();
  }

  public void addToElementKeys(ByteBuffer elem) {
    if (this.elementKeys == null) {
      this.elementKeys = new ArrayList<ByteBuffer>();
    }
    this.elementKeys.add(elem);
  }

  public List<ByteBuffer> getElementKeys() {
    return this.elementKeys;
  }

  public TORSet setElementKeys(List<ByteBuffer> elementKeys) {
    this.elementKeys = elementKeys;
    return this;
  }

  public void unsetElementKeys() {
    this.elementKeys = null;
  }

  /** Returns true if field elementKeys is set (has been assigned a value) and false otherwise */
  public boolean isSetElementKeys() {
    return this.elementKeys != null;
  }

  public void setElementKeysIsSet(boolean value) {
    if (!value) {
      this.elementKeys = null;
    }
  }

  public int getElementValuesSize() {
    return (this.elementValues == null) ? 0 : this.elementValues.size();
  }

  public java.util.Iterator<TVersionVector> getElementValuesIterator() {
    return (this.elementValues == null) ? null : this.elementValues.iterator();
  }

  public void addToElementValues(TVersionVector elem) {
    if (this.elementValues == null) {
      this.elementValues = new ArrayList<TVersionVector>();
    }
    this.elementValues.add(elem);
  }

  public List<TVersionVector> getElementValues() {
    return this.elementValues;
  }

  public TORSet setElementValues(List<TVersionVector> elementValues) {
    this.elementValues = elementValues;
    return this;
  }

  public void unsetElementValues() {
    this.elementValues = null;
  }

  /** Returns true if field elementValues is set (has been assigned a value) and false otherwise */
  public boolean isSetElementValues() {
    return this.elementValues != null;
  }

  public void setElementValuesIsSet(boolean value) {
    if (!value) {
      this.elementValues = null;
    }
  }

  public TVersionVector getVersionVector() {
    return this.versionVector;
  }

  public TORSet setVersionVector(TVersionVector versionVector) {
    this.versionVector = versionVector;
    return this;
  }

  public void unsetVersionVector() {
    this.versionVector = null;
  }

  /** Returns true if field versionVector is set (has been assigned a value) and false otherwise */
  public boolean isSetVersionVector() {
    return this.versionVector != null;
  }

  public void setVersionVectorIsSet(boolean value) {
    if (!value) {
      this.versionVector = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ELEMENT_TYPE:
      if (value == null) {
        unsetElementType();
      } else {
        setElementType((Integer)value);
      }
      break;

    case ELEMENT_KEYS:
      if (value == null) {
        unsetElementKeys();
      } else {
        setElementKeys((List<ByteBuffer>)value);
      }
      break;

    case ELEMENT_VALUES:
      if (value == null) {
        unsetElementValues();
      } else {
        setElementValues((List<TVersionVector>)value);
      }
      break;

    case VERSION_VECTOR:
      if (value == null) {
        unsetVersionVector();
      } else {
        setVersionVector((TVersionVector)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ELEMENT_TYPE:
      return getElementType();

    case ELEMENT_KEYS:
      return getElementKeys();

    case ELEMENT_VALUES:
      return getElementValues();

    case VERSION_VECTOR:
      return getVersionVector();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ELEMENT_TYPE:
      return isSetElementType();
    case ELEMENT_KEYS:
      return isSetElementKeys();
    case ELEMENT_VALUES:
      return isSetElementValues();
    case VERSION_VECTOR:
      return isSetVersionVector();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TORSet)
      return this.equals((TORSet)that);
    return false;
  }

  public boolean equals(TORSet that) {
    if (that == null)
      return false;

    boolean this_present_elementType = true;
    boolean that_present_elementType = true;
    if (this_present_elementType || that_present_elementType) {
      if (!(this_present_elementType && that_present_elementType))
        return false;
      if (this.elementType != that.elementType)
        return false;
    }

    boolean this_present_elementKeys = true && this.isSetElementKeys();
    boolean that_present_elementKeys = true && that.isSetElementKeys();
    if (this_present_elementKeys || that_present_elementKeys) {
      if (!(this_present_elementKeys && that_present_elementKeys))
        return false;
      if (!this.elementKeys.equals(that.elementKeys))
        return false;
    }

    boolean this_present_elementValues = true && this.isSetElementValues();
    boolean that_present_elementValues = true && that.isSetElementValues();
    if (this_present_elementValues || that_present_elementValues) {
      if (!(this_present_elementValues && that_present_elementValues))
        return false;
      if (!this.elementValues.equals(that.elementValues))
        return false;
    }

    boolean this_present_versionVector = true && this.isSetVersionVector();
    boolean that_present_versionVector = true && that.isSetVersionVector();
    if (this_present_versionVector || that_present_versionVector) {
      if (!(this_present_versionVector && that_present_versionVector))
        return false;
      if (!this.versionVector.equals(that.versionVector))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_elementType = true;
    list.add(present_elementType);
    if (present_elementType)
      list.add(elementType);

    boolean present_elementKeys = true && (isSetElementKeys());
    list.add(present_elementKeys);
    if (present_elementKeys)
      list.add(elementKeys);

    boolean present_elementValues = true && (isSetElementValues());
    list.add(present_elementValues);
    if (present_elementValues)
      list.add(elementValues);

    boolean present_versionVector = true && (isSetVersionVector());
    list.add(present_versionVector);
    if (present_versionVector)
      list.add(versionVector);

    return list.hashCode();
  }

  @Override
  public int compareTo(TORSet other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetElementType()).compareTo(other.isSetElementType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetElementType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.elementType, other.elementType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetElementKeys()).compareTo(other.isSetElementKeys());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetElementKeys()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.elementKeys, other.elementKeys);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetElementValues()).compareTo(other.isSetElementValues());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetElementValues()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.elementValues, other.elementValues);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVersionVector()).compareTo(other.isSetVersionVector());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVersionVector()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.versionVector, other.versionVector);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TORSet(");
    boolean first = true;

    sb.append("elementType:");
    sb.append(this.elementType);
    first = false;
    if (!first) sb.append(", ");
    sb.append("elementKeys:");
    if (this.elementKeys == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.elementKeys, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("elementValues:");
    if (this.elementValues == null) {
      sb.append("null");
    } else {
      sb.append(this.elementValues);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("versionVector:");
    if (this.versionVector == null) {
      sb.append("null");
    } else {
      sb.append(this.versionVector);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TORSetStandardSchemeFactory implements SchemeFactory {
    public TORSetStandardScheme getScheme() {
      return new TORSetStandardScheme();
    }
  }

  private static class TORSetStandardScheme extends StandardScheme<TORSet> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TORSet struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ELEMENT_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.elementType = iprot.readI32();
              struct.setElementTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ELEMENT_KEYS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.elementKeys = new ArrayList<ByteBuffer>(_list0.size);
                ByteBuffer _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = iprot.readBinary();
                  struct.elementKeys.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setElementKeysIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ELEMENT_VALUES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list3 = iprot.readListBegin();
                struct.elementValues = new ArrayList<TVersionVector>(_list3.size);
                TVersionVector _elem4;
                for (int _i5 = 0; _i5 < _list3.size; ++_i5)
                {
                  _elem4 = new TVersionVector();
                  _elem4.read(iprot);
                  struct.elementValues.add(_elem4);
                }
                iprot.readListEnd();
              }
              struct.setElementValuesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // VERSION_VECTOR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.versionVector = new TVersionVector();
              struct.versionVector.read(iprot);
              struct.setVersionVectorIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TORSet struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ELEMENT_TYPE_FIELD_DESC);
      oprot.writeI32(struct.elementType);
      oprot.writeFieldEnd();
      if (struct.elementKeys != null) {
        oprot.writeFieldBegin(ELEMENT_KEYS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.elementKeys.size()));
          for (ByteBuffer _iter6 : struct.elementKeys)
          {
            oprot.writeBinary(_iter6);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.elementValues != null) {
        oprot.writeFieldBegin(ELEMENT_VALUES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.elementValues.size()));
          for (TVersionVector _iter7 : struct.elementValues)
          {
            _iter7.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.versionVector != null) {
        oprot.writeFieldBegin(VERSION_VECTOR_FIELD_DESC);
        struct.versionVector.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TORSetTupleSchemeFactory implements SchemeFactory {
    public TORSetTupleScheme getScheme() {
      return new TORSetTupleScheme();
    }
  }

  private static class TORSetTupleScheme extends TupleScheme<TORSet> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TORSet struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetElementType()) {
        optionals.set(0);
      }
      if (struct.isSetElementKeys()) {
        optionals.set(1);
      }
      if (struct.isSetElementValues()) {
        optionals.set(2);
      }
      if (struct.isSetVersionVector()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetElementType()) {
        oprot.writeI32(struct.elementType);
      }
      if (struct.isSetElementKeys()) {
        {
          oprot.writeI32(struct.elementKeys.size());
          for (ByteBuffer _iter8 : struct.elementKeys)
          {
            oprot.writeBinary(_iter8);
          }
        }
      }
      if (struct.isSetElementValues()) {
        {
          oprot.writeI32(struct.elementValues.size());
          for (TVersionVector _iter9 : struct.elementValues)
          {
            _iter9.write(oprot);
          }
        }
      }
      if (struct.isSetVersionVector()) {
        struct.versionVector.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TORSet struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.elementType = iprot.readI32();
        struct.setElementTypeIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list10 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.elementKeys = new ArrayList<ByteBuffer>(_list10.size);
          ByteBuffer _elem11;
          for (int _i12 = 0; _i12 < _list10.size; ++_i12)
          {
            _elem11 = iprot.readBinary();
            struct.elementKeys.add(_elem11);
          }
        }
        struct.setElementKeysIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.elementValues = new ArrayList<TVersionVector>(_list13.size);
          TVersionVector _elem14;
          for (int _i15 = 0; _i15 < _list13.size; ++_i15)
          {
            _elem14 = new TVersionVector();
            _elem14.read(iprot);
            struct.elementValues.add(_elem14);
          }
        }
        struct.setElementValuesIsSet(true);
      }
      if (incoming.get(3)) {
        struct.versionVector = new TVersionVector();
        struct.versionVector.read(iprot);
        struct.setVersionVectorIsSet(true);
      }
    }
  }

}

