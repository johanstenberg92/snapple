#@namespace scala snapple.finagle.io

struct TVersionVector {
  1: map<string, i64> versions
}

struct TORSet {
  1: i32 elementKind,
  2: map<binary, TVersionVector> elements,
  4: TVersionVector versionVector
}

union TDataType {
  1: TORSet orset,
  2: TVersionVector versionVector
}

struct TOptionalDataType {
  1: optional TDataType dataType
}

service SnappleService {

  void ping()

  # methods for replica clients
  void propagate(1: map<string, TDataType> values)

  # methods for the external clients
  bool createEntry(1: string key, 2: string dataKind, 3: i32 elementKind),

  bool removeEntry(1: string key),

  TOptionalDataType getEntry(1: string key),

  bool modifyEntry(1: string key, 2: string operation, 3: binary element),

}
