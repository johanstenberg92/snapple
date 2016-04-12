namespace java snapple.thrift.io

const i32 NO_ELEMENT_TYPE = 0;
const i32 BOOL_ELEMENT_TYPE = 1;
const i32 BYTE_ELEMENT_TYPE = 2;
const i32 INT_ELEMENT_TYPE = 3;
const i32 LONG_ELEMENT_TYPE = 4;
const i32 DOUBLE_ELEMENT_TYPE = 5;
const i32 STRING_ELEMENT_TYPE = 6;

union TDataType {
  1: TORSet orset,
  2: TVersionVector versionVector
}

struct TORSet {
  1: i32 elementType,
  2: map<binary, TVersionVector> elementsMap,
  3: TVersionVector versionVector
}

struct TVersionVector {
  1: map<string, i64> versions
}

struct TOptionalDataType {
  1: optional TDataType dataType
}

service SnappleService {

  void ping()

  # methods for replica clients
  void propagate(1: map<string, TDataType> values)

  # methods for the external clients
  bool createEntry(1: string key, 2: string dataType, 3: i32 elementType),

  bool removeEntry(1: string key),

  TOptionalDataType getEntry(1: string key),

  bool modifyEntry(1: string key, 2: string operation, 3: binary element)

}
