/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package snapple.kv.io;

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
public class TElementType extends org.apache.thrift.TUnion<TElementType, TElementType._Fields> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TElementType");
  private static final org.apache.thrift.protocol.TField BOOLEAN_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("booleanValue", org.apache.thrift.protocol.TType.BOOL, (short)1);
  private static final org.apache.thrift.protocol.TField BYTE_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("byteValue", org.apache.thrift.protocol.TType.BYTE, (short)2);
  private static final org.apache.thrift.protocol.TField INT_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("intValue", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField LONG_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("longValue", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField DOUBLE_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("doubleValue", org.apache.thrift.protocol.TType.DOUBLE, (short)5);
  private static final org.apache.thrift.protocol.TField STRING_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("stringValue", org.apache.thrift.protocol.TType.STRING, (short)6);

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    BOOLEAN_VALUE((short)1, "booleanValue"),
    BYTE_VALUE((short)2, "byteValue"),
    INT_VALUE((short)3, "intValue"),
    LONG_VALUE((short)4, "longValue"),
    DOUBLE_VALUE((short)5, "doubleValue"),
    STRING_VALUE((short)6, "stringValue");

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
        case 1: // BOOLEAN_VALUE
          return BOOLEAN_VALUE;
        case 2: // BYTE_VALUE
          return BYTE_VALUE;
        case 3: // INT_VALUE
          return INT_VALUE;
        case 4: // LONG_VALUE
          return LONG_VALUE;
        case 5: // DOUBLE_VALUE
          return DOUBLE_VALUE;
        case 6: // STRING_VALUE
          return STRING_VALUE;
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

  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.BOOLEAN_VALUE, new org.apache.thrift.meta_data.FieldMetaData("booleanValue", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.BYTE_VALUE, new org.apache.thrift.meta_data.FieldMetaData("byteValue", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    tmpMap.put(_Fields.INT_VALUE, new org.apache.thrift.meta_data.FieldMetaData("intValue", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LONG_VALUE, new org.apache.thrift.meta_data.FieldMetaData("longValue", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.DOUBLE_VALUE, new org.apache.thrift.meta_data.FieldMetaData("doubleValue", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.STRING_VALUE, new org.apache.thrift.meta_data.FieldMetaData("stringValue", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TElementType.class, metaDataMap);
  }

  public TElementType() {
    super();
  }

  public TElementType(_Fields setField, Object value) {
    super(setField, value);
  }

  public TElementType(TElementType other) {
    super(other);
  }
  public TElementType deepCopy() {
    return new TElementType(this);
  }

  public static TElementType booleanValue(boolean value) {
    TElementType x = new TElementType();
    x.setBooleanValue(value);
    return x;
  }

  public static TElementType byteValue(byte value) {
    TElementType x = new TElementType();
    x.setByteValue(value);
    return x;
  }

  public static TElementType intValue(int value) {
    TElementType x = new TElementType();
    x.setIntValue(value);
    return x;
  }

  public static TElementType longValue(long value) {
    TElementType x = new TElementType();
    x.setLongValue(value);
    return x;
  }

  public static TElementType doubleValue(double value) {
    TElementType x = new TElementType();
    x.setDoubleValue(value);
    return x;
  }

  public static TElementType stringValue(String value) {
    TElementType x = new TElementType();
    x.setStringValue(value);
    return x;
  }


  @Override
  protected void checkType(_Fields setField, Object value) throws ClassCastException {
    switch (setField) {
      case BOOLEAN_VALUE:
        if (value instanceof Boolean) {
          break;
        }
        throw new ClassCastException("Was expecting value of type Boolean for field 'booleanValue', but got " + value.getClass().getSimpleName());
      case BYTE_VALUE:
        if (value instanceof Byte) {
          break;
        }
        throw new ClassCastException("Was expecting value of type Byte for field 'byteValue', but got " + value.getClass().getSimpleName());
      case INT_VALUE:
        if (value instanceof Integer) {
          break;
        }
        throw new ClassCastException("Was expecting value of type Integer for field 'intValue', but got " + value.getClass().getSimpleName());
      case LONG_VALUE:
        if (value instanceof Long) {
          break;
        }
        throw new ClassCastException("Was expecting value of type Long for field 'longValue', but got " + value.getClass().getSimpleName());
      case DOUBLE_VALUE:
        if (value instanceof Double) {
          break;
        }
        throw new ClassCastException("Was expecting value of type Double for field 'doubleValue', but got " + value.getClass().getSimpleName());
      case STRING_VALUE:
        if (value instanceof String) {
          break;
        }
        throw new ClassCastException("Was expecting value of type String for field 'stringValue', but got " + value.getClass().getSimpleName());
      default:
        throw new IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected Object standardSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TField field) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(field.id);
    if (setField != null) {
      switch (setField) {
        case BOOLEAN_VALUE:
          if (field.type == BOOLEAN_VALUE_FIELD_DESC.type) {
            Boolean booleanValue;
            booleanValue = iprot.readBool();
            return booleanValue;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case BYTE_VALUE:
          if (field.type == BYTE_VALUE_FIELD_DESC.type) {
            Byte byteValue;
            byteValue = iprot.readByte();
            return byteValue;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case INT_VALUE:
          if (field.type == INT_VALUE_FIELD_DESC.type) {
            Integer intValue;
            intValue = iprot.readI32();
            return intValue;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case LONG_VALUE:
          if (field.type == LONG_VALUE_FIELD_DESC.type) {
            Long longValue;
            longValue = iprot.readI64();
            return longValue;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case DOUBLE_VALUE:
          if (field.type == DOUBLE_VALUE_FIELD_DESC.type) {
            Double doubleValue;
            doubleValue = iprot.readDouble();
            return doubleValue;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case STRING_VALUE:
          if (field.type == STRING_VALUE_FIELD_DESC.type) {
            String stringValue;
            stringValue = iprot.readString();
            return stringValue;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        default:
          throw new IllegalStateException("setField wasn't null, but didn't match any of the case statements!");
      }
    } else {
      org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
      return null;
    }
  }

  @Override
  protected void standardSchemeWriteValue(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    switch (setField_) {
      case BOOLEAN_VALUE:
        Boolean booleanValue = (Boolean)value_;
        oprot.writeBool(booleanValue);
        return;
      case BYTE_VALUE:
        Byte byteValue = (Byte)value_;
        oprot.writeByte(byteValue);
        return;
      case INT_VALUE:
        Integer intValue = (Integer)value_;
        oprot.writeI32(intValue);
        return;
      case LONG_VALUE:
        Long longValue = (Long)value_;
        oprot.writeI64(longValue);
        return;
      case DOUBLE_VALUE:
        Double doubleValue = (Double)value_;
        oprot.writeDouble(doubleValue);
        return;
      case STRING_VALUE:
        String stringValue = (String)value_;
        oprot.writeString(stringValue);
        return;
      default:
        throw new IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected Object tupleSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, short fieldID) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(fieldID);
    if (setField != null) {
      switch (setField) {
        case BOOLEAN_VALUE:
          Boolean booleanValue;
          booleanValue = iprot.readBool();
          return booleanValue;
        case BYTE_VALUE:
          Byte byteValue;
          byteValue = iprot.readByte();
          return byteValue;
        case INT_VALUE:
          Integer intValue;
          intValue = iprot.readI32();
          return intValue;
        case LONG_VALUE:
          Long longValue;
          longValue = iprot.readI64();
          return longValue;
        case DOUBLE_VALUE:
          Double doubleValue;
          doubleValue = iprot.readDouble();
          return doubleValue;
        case STRING_VALUE:
          String stringValue;
          stringValue = iprot.readString();
          return stringValue;
        default:
          throw new IllegalStateException("setField wasn't null, but didn't match any of the case statements!");
      }
    } else {
      throw new TProtocolException("Couldn't find a field with field id " + fieldID);
    }
  }

  @Override
  protected void tupleSchemeWriteValue(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    switch (setField_) {
      case BOOLEAN_VALUE:
        Boolean booleanValue = (Boolean)value_;
        oprot.writeBool(booleanValue);
        return;
      case BYTE_VALUE:
        Byte byteValue = (Byte)value_;
        oprot.writeByte(byteValue);
        return;
      case INT_VALUE:
        Integer intValue = (Integer)value_;
        oprot.writeI32(intValue);
        return;
      case LONG_VALUE:
        Long longValue = (Long)value_;
        oprot.writeI64(longValue);
        return;
      case DOUBLE_VALUE:
        Double doubleValue = (Double)value_;
        oprot.writeDouble(doubleValue);
        return;
      case STRING_VALUE:
        String stringValue = (String)value_;
        oprot.writeString(stringValue);
        return;
      default:
        throw new IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TField getFieldDesc(_Fields setField) {
    switch (setField) {
      case BOOLEAN_VALUE:
        return BOOLEAN_VALUE_FIELD_DESC;
      case BYTE_VALUE:
        return BYTE_VALUE_FIELD_DESC;
      case INT_VALUE:
        return INT_VALUE_FIELD_DESC;
      case LONG_VALUE:
        return LONG_VALUE_FIELD_DESC;
      case DOUBLE_VALUE:
        return DOUBLE_VALUE_FIELD_DESC;
      case STRING_VALUE:
        return STRING_VALUE_FIELD_DESC;
      default:
        throw new IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TStruct getStructDesc() {
    return STRUCT_DESC;
  }

  @Override
  protected _Fields enumForId(short id) {
    return _Fields.findByThriftIdOrThrow(id);
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }


  public boolean getBooleanValue() {
    if (getSetField() == _Fields.BOOLEAN_VALUE) {
      return (Boolean)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'booleanValue' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setBooleanValue(boolean value) {
    setField_ = _Fields.BOOLEAN_VALUE;
    value_ = value;
  }

  public byte getByteValue() {
    if (getSetField() == _Fields.BYTE_VALUE) {
      return (Byte)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'byteValue' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setByteValue(byte value) {
    setField_ = _Fields.BYTE_VALUE;
    value_ = value;
  }

  public int getIntValue() {
    if (getSetField() == _Fields.INT_VALUE) {
      return (Integer)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'intValue' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setIntValue(int value) {
    setField_ = _Fields.INT_VALUE;
    value_ = value;
  }

  public long getLongValue() {
    if (getSetField() == _Fields.LONG_VALUE) {
      return (Long)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'longValue' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setLongValue(long value) {
    setField_ = _Fields.LONG_VALUE;
    value_ = value;
  }

  public double getDoubleValue() {
    if (getSetField() == _Fields.DOUBLE_VALUE) {
      return (Double)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'doubleValue' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setDoubleValue(double value) {
    setField_ = _Fields.DOUBLE_VALUE;
    value_ = value;
  }

  public String getStringValue() {
    if (getSetField() == _Fields.STRING_VALUE) {
      return (String)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'stringValue' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setStringValue(String value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.STRING_VALUE;
    value_ = value;
  }

  public boolean isSetBooleanValue() {
    return setField_ == _Fields.BOOLEAN_VALUE;
  }


  public boolean isSetByteValue() {
    return setField_ == _Fields.BYTE_VALUE;
  }


  public boolean isSetIntValue() {
    return setField_ == _Fields.INT_VALUE;
  }


  public boolean isSetLongValue() {
    return setField_ == _Fields.LONG_VALUE;
  }


  public boolean isSetDoubleValue() {
    return setField_ == _Fields.DOUBLE_VALUE;
  }


  public boolean isSetStringValue() {
    return setField_ == _Fields.STRING_VALUE;
  }


  public boolean equals(Object other) {
    if (other instanceof TElementType) {
      return equals((TElementType)other);
    } else {
      return false;
    }
  }

  public boolean equals(TElementType other) {
    return other != null && getSetField() == other.getSetField() && getFieldValue().equals(other.getFieldValue());
  }

  @Override
  public int compareTo(TElementType other) {
    int lastComparison = org.apache.thrift.TBaseHelper.compareTo(getSetField(), other.getSetField());
    if (lastComparison == 0) {
      return org.apache.thrift.TBaseHelper.compareTo(getFieldValue(), other.getFieldValue());
    }
    return lastComparison;
  }


  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();
    list.add(this.getClass().getName());
    org.apache.thrift.TFieldIdEnum setField = getSetField();
    if (setField != null) {
      list.add(setField.getThriftFieldId());
      Object value = getFieldValue();
      if (value instanceof org.apache.thrift.TEnum) {
        list.add(((org.apache.thrift.TEnum)getFieldValue()).getValue());
      } else {
        list.add(value);
      }
    }
    return list.hashCode();
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }


}
