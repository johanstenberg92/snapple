namespace java snapple.thrift.io

union TDataType {
  1: TORSet orset,
  2: TVersionVector versionVector
}

struct TORSet {
  1: i32 elementType,
  2: list<binary> elementKeys,
  3: list<TVersionVector> elementValues,
  4: TVersionVector versionVector
}

struct TVersionVector {
  1: list<string> versionKeys,
  2: list<i64> versionValues,
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

  bool modifyEntry(1: string key, 2: string operation, 3: binary element),

}
