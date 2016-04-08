namespace java snapple.thrift.io

union TElementType {
  1: bool booleanValue,
  2: byte byteValue,
  3: i32 intValue,
  4: i64 longValue,
  5: double doubleValue,
  6: string stringValue
}

union TDataType {
  1: TORSet orset,
  2: TVersionVector versionVector
}

struct TORSet {
  1: map<TElementType, TVersionVector> elementsMap,
  2: TVersionVector versionVector
}

struct TVersionVector {
  1: map<string, i64> versions
}

struct TOptionalElementType {
  1: optional TElementType elementType
}

struct TOptionalString {
  1: optional string s
}

struct TOptionalDataType {
  1: optional TDataType dataType
}

service SnappleService {

  void ping()

  # methods for replica clients
  void propagate(1: map<string, TDataType> values)

  # methods for the external clients
  bool createEntry(1: string key, 2: string dataType, 3: TOptionalString elementType),

  bool removeEntry(1: string key),

  TOptionalDataType getEntry(1: string key),

  bool modifyEntry(1: string key, 2: string operation, 3: TOptionalElementType element)

}
